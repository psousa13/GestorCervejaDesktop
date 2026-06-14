import React, { useMemo, useState, useEffect, useCallback } from "react";
import {
  BadgeCheck, Beer, Building2, CalendarClock, Check, ChevronLeft,
  ClipboardList, LogOut, Minus, Package, PackageCheck, Plus, ReceiptText,
  RefreshCw, Search, Settings, ShoppingCart, Trash2, User
} from "lucide-react";
import {
  CRATE_LITERS, clearSessionCustomer, fetchOrders, fetchRecipes,
  getSessionCustomer, mapRecipe, submitOrderToBackend
} from "./services/store.js";
import AdminPanel from "./components/AdminPanel.jsx";
import ClienteAuth from "./components/ClienteAuth.jsx";

const formatter = new Intl.NumberFormat("pt-PT", { style: "currency", currency: "EUR" });
const price = v => formatter.format(v);

function estimatedDate(days = 12) {
  const d = new Date(); d.setDate(d.getDate() + days); return d.toISOString().slice(0, 10);
}

export default function App() {
  const [sessionCustomer, setSessionCustomer] = useState(() => getSessionCustomer());
  const [query, setQuery]                     = useState("");
  const [cart, setCart]                       = useState({});
  const [backendOrders, setBackendOrders]     = useState([]);
  const [activeView, setActiveView]           = useState("loja");
  const [lastOrder, setLastOrder]             = useState(null);
  const [products, setProducts]               = useState([]);
  const [loadingProducts, setLoadingProducts] = useState(false);
  const [productsError, setProductsError]     = useState("");

  useEffect(() => {
    if (!sessionCustomer) return;
    setLoadingProducts(true);
    setProductsError("");
    fetchRecipes()
      .then(data => setProducts(data.map(mapRecipe)))
      .catch(err => setProductsError(err.message))
      .finally(() => setLoadingProducts(false));
  }, [sessionCustomer]);

  const loadBackendOrders = useCallback(() => {
    const email = sessionCustomer?.email?.trim();
    if (!email) return;
    fetchOrders(email).then(setBackendOrders).catch(() => setBackendOrders([]));
  }, [sessionCustomer]);

  useEffect(() => {
    if (activeView === "encomendas" && sessionCustomer) loadBackendOrders();
  }, [activeView, sessionCustomer, loadBackendOrders]);

  const visibleProducts = useMemo(() =>
    products.filter(p => {
      const hay = `${p.name} ${p.description}`.toLowerCase();
      return hay.includes(query.toLowerCase());
    }), [products, query]);

  const cartItems = useMemo(() =>
    Object.entries(cart).map(([id, quantity]) => {
      const p = products.find(x => x.id === Number(id));
      return p ? {
        ...p, quantity,
        liters: quantity * CRATE_LITERS,
        subtotal: quantity * CRATE_LITERS * p.pricePerLiter,
      } : null;
    }).filter(Boolean), [cart, products]);

  const totals = useMemo(() => {
    const totalCrates = cartItems.reduce((s, i) => s + i.quantity, 0);
    const totalLiters = cartItems.reduce((s, i) => s + i.liters, 0);
    const subtotal    = cartItems.reduce((s, i) => s + i.subtotal, 0);
    const discount    = sessionCustomer?.tipoCliente === "Revendedor" && subtotal >= 150 ? subtotal * 0.08 : 0;
    const delivery    = subtotal >= 120 || subtotal === 0 ? 0 : 9.5;
    const iva         = (subtotal - discount + delivery) * 0.23;
    return { totalCrates, totalLiters, subtotal, discount, delivery, iva, total: subtotal - discount + delivery + iva };
  }, [cartItems, sessionCustomer?.tipoCliente]);

  function addToCart(id)                       { setCart(c => ({ ...c, [id]: (c[id] || 0) + 1 })); }
  function updateQuantity(id, qty)             { setCart(c => { const n = {...c}; qty <= 0 ? delete n[id] : (n[id] = qty); return n; }); }
  function handleLogout() {
    clearSessionCustomer();
    setSessionCustomer(null);
    setCart({});
    setActiveView("loja");
  }

  async function submitOrder(e, extras = {}) {
    e.preventDefault();
    if (!cartItems.length || !sessionCustomer) return;

    const estimDays = sessionCustomer.tipoCliente === "Revendedor" ? 15 : 10;
    const customer = { ...sessionCustomer, ...extras };
    const order = {
      id: `PED-${Date.now().toString().slice(-6)}`,
      dataPedido: new Date().toISOString().slice(0, 10),
      dataEstimadaConclusao: estimatedDate(estimDays),
      estado: "Pendente",
      customer,
      items: cartItems,
      totals
    };

    try {
      const backendRes = await submitOrderToBackend(order);
      if (backendRes?.numeroPedido) order.id = backendRes.numeroPedido;
    } catch (err) {
      console.warn("Erro ao enviar pedido:", err.message);
    }

    setLastOrder(order);
    setCart({});
    setActiveView("confirmacao");
  }

  if (activeView === "admin") return <AdminPanel onBack={() => setActiveView("loja")} />;

  if (!sessionCustomer) {
    return <ClienteAuth onAuthenticated={setSessionCustomer} />;
  }

  const customerName = sessionCustomer.tipoCliente === "Revendedor"
    ? sessionCustomer.nomeEmpresa
    : sessionCustomer.nomeCompleto;

  return (
    <div className="app-shell">
      <header className="topbar">
        <a className="brand" href="#catalogo" onClick={() => setActiveView("loja")}>
          <span className="brand-mark"><Beer size={25} /></span>
          <span><strong>Neo Cerveja</strong><small>Loja online</small></span>
        </a>
        <nav aria-label="Navegação principal">
          <button className={activeView === "loja" ? "active" : ""} onClick={() => setActiveView("loja")}>Loja</button>
          <button className={activeView === "encomendas" ? "active" : ""} onClick={() => setActiveView("encomendas")}>Encomendas</button>
        </nav>
        <div style={{ display: "flex", alignItems: "center", gap: 8 }}>
          <span className="session-badge" title={sessionCustomer.email}>
            <User size={14} /> {customerName || sessionCustomer.email}
          </span>
          <button className="cart-pill" onClick={() => setActiveView("checkout")} aria-label="Abrir carrinho">
            <ShoppingCart size={18} /><span>{totals.totalCrates} grades</span>
          </button>
          <button className="ghost-button session-logout" onClick={handleLogout} title="Terminar sessão">
            <LogOut size={16} />
          </button>
          <button onClick={() => setActiveView("admin")}
            title="Administração"
            style={{ background: "rgba(123,45,62,0.08)", border: "1px solid rgba(123,45,62,0.15)", borderRadius: 8,
              padding: "7px 10px", cursor: "pointer", color: "#7B2D3E", display: "flex", alignItems: "center" }}>
            <Settings size={16} />
          </button>
        </div>
      </header>

      <main>
        {activeView === "loja" && (
          <>
            <section className="hero">
              <div className="hero-copy">
                <p className="eyebrow">Produção rastreável, compra simples</p>
                <h1>Neo Cerveja</h1>
                <p>Encomende receitas disponíveis, acompanhe o pedido e envie todos os dados para faturação.</p>
                <div className="hero-actions">
                  <a href="#catalogo" className="primary-action">Ver catálogo</a>
                  <button className="secondary-action" onClick={() => setActiveView("checkout")}>
                    <ShoppingCart size={18} /> Carrinho
                  </button>
                </div>
              </div>
              <div className="hero-media" aria-label="Garrafas de cerveja Neo Cerveja">
                <img src="/assets/hero-bottles.png" alt="Seleção de garrafas Neo Cerveja" />
              </div>
            </section>

            <section className="store-layout" id="catalogo">
              <div className="catalogue">
                <div className="section-heading">
                  <div>
                    <p className="eyebrow">Catálogo de produtos</p>
                    <h2>
                      Cervejas disponíveis
                      {loadingProducts && <span style={{ fontSize: 12, color: "#8C8480" }}> · a carregar…</span>}
                    </h2>
                  </div>
                  <div className="searchbox">
                    <Search size={18} />
                    <input type="search" placeholder="Pesquisar receita" value={query} onChange={e => setQuery(e.target.value)} />
                  </div>
                </div>
                {productsError && <p className="catalog-error">{productsError}</p>}
                {!loadingProducts && !productsError && visibleProducts.length === 0 && (
                  <div className="empty-state"><Package size={32} /><p>Nenhuma receita disponível no momento.</p></div>
                )}
                <div className="product-grid">
                  {visibleProducts.map(p => (
                    <article className="product-card" key={p.id}>
                      <div className="product-image product-image--placeholder" aria-hidden="true">
                        <Beer size={72} strokeWidth={1.2} />
                      </div>
                      <div className="product-body">
                        <div className="product-title">
                          <div><h3>{p.name}</h3></div>
                          <strong>{price(p.pricePerLiter)}/L</strong>
                        </div>
                        <p>{p.description || "Receita artesanal Neo Cerveja."}</p>
                        <p className="product-grade-hint">Grade {CRATE_LITERS} L · {price(p.pricePerGrade)}/grade</p>
                        <button className="add-button" onClick={() => addToCart(p.id)}>
                          <Plus size={18} /> Adicionar grade
                        </button>
                      </div>
                    </article>
                  ))}
                </div>
              </div>
              <CartPanel cartItems={cartItems} totals={totals} updateQuantity={updateQuantity} openCheckout={() => setActiveView("checkout")} />
            </section>
          </>
        )}

        {activeView === "checkout" && (
          <CheckoutView customer={sessionCustomer} cartItems={cartItems} totals={totals}
            onSubmit={(e, extras) => submitOrder(e, extras)} updateQuantity={updateQuantity} backToStore={() => setActiveView("loja")} />
        )}

        {activeView === "confirmacao" && (
          <Confirmation order={lastOrder} onContinue={() => setActiveView("loja")} />
        )}

        {activeView === "encomendas" && (
          <OrdersView backendOrders={backendOrders} onRefresh={loadBackendOrders} />
        )}
      </main>
    </div>
  );
}

function CartPanel({ cartItems, totals, updateQuantity, openCheckout }) {
  return (
    <aside className="cart-panel" aria-label="Resumo do carrinho">
      <div className="panel-title"><div><p className="eyebrow">Pedido</p><h2>Carrinho</h2></div><ShoppingCart size={22} /></div>
      {cartItems.length ? (
        <div className="cart-lines">{cartItems.map(i => <CartLine key={i.id} item={i} updateQuantity={updateQuantity} />)}</div>
      ) : (
        <div className="empty-state"><PackageCheck size={32} /><p>Escolha as receitas para preparar a encomenda.</p></div>
      )}
      <Totals totals={totals} />
      <button className="checkout-button" disabled={!cartItems.length} onClick={openCheckout}>
        <ReceiptText size={18} /> Finalizar encomenda
      </button>
    </aside>
  );
}

function CartLine({ item, updateQuantity }) {
  return (
    <div className="cart-line">
      <div className="cart-line-icon"><Beer size={20} /></div>
      <div><strong>{item.name}</strong><span>{item.liters.toFixed(2)} L · {price(item.subtotal)}</span></div>
      <div className="quantity">
        <button onClick={() => updateQuantity(item.id, item.quantity - 1)} aria-label="Diminuir"><Minus size={15} /></button>
        <span>{item.quantity}</span>
        <button onClick={() => updateQuantity(item.id, item.quantity + 1)} aria-label="Aumentar"><Plus size={15} /></button>
      </div>
    </div>
  );
}

function Totals({ totals }) {
  return (
    <div className="totals">
      <div><span>Grades</span><strong>{totals.totalCrates}</strong></div>
      <div><span>Litros</span><strong>{totals.totalLiters.toFixed(2)} L</strong></div>
      <div><span>Subtotal</span><strong>{price(totals.subtotal)}</strong></div>
      {totals.discount > 0 && <div><span>Desconto revendedor</span><strong>-{price(totals.discount)}</strong></div>}
      <div><span>Entrega</span><strong>{totals.delivery === 0 ? "Incluída" : price(totals.delivery)}</strong></div>
      <div><span>IVA 23%</span><strong>{price(totals.iva)}</strong></div>
      <div className="grand-total"><span>Total</span><strong>{price(totals.total)}</strong></div>
    </div>
  );
}

function CheckoutView({ customer, cartItems, totals, onSubmit, updateQuantity, backToStore }) {
  const isCompany = customer.tipoCliente === "Revendedor";
  const [morada, setMorada] = useState(customer.morada || "");
  const [notas, setNotas]   = useState(customer.notas || "");

  function handleSubmit(e) {
    e.preventDefault();
    onSubmit(e, { morada, notas });
  }

  return (
    <section className="checkout-view">
      <div className="checkout-main">
        <div className="section-heading">
          <div><p className="eyebrow">Checkout</p><h1>Finalizar encomenda</h1></div>
          <button className="ghost-button" onClick={backToStore}>Voltar à loja</button>
        </div>
        <form className="checkout-form" onSubmit={handleSubmit}>
          <fieldset>
            <legend>Tipo de cliente</legend>
            <div className="client-toggle client-toggle--readonly">
              <span className={!isCompany ? "checked" : ""}><User size={18} /> Particular</span>
              <span className={isCompany ? "checked" : ""}><Building2 size={18} /> Revendedor</span>
            </div>
          </fieldset>
          <fieldset>
            <legend>Dados de faturação</legend>
            <div className="form-grid">
              {isCompany ? (<>
                <ReadonlyField label="Empresa" value={customer.nomeEmpresa} />
                <ReadonlyField label="VAT / NIF" value={customer.vatEmpresa} />
                <ReadonlyField label="Contacto principal" value={customer.contactoPrincipal} />
              </>) : (<>
                <ReadonlyField label="Nome completo" value={customer.nomeCompleto} />
                <ReadonlyField label="NIF" value={customer.nif} />
              </>)}
              <ReadonlyField label="Email" value={customer.email} />
              <ReadonlyField label="Telefone" value={customer.telefone} />
              <label className="full-span">Morada de entrega<textarea value={morada} onChange={e => setMorada(e.target.value)} required rows="3" /></label>
              <label className="full-span">Observações<textarea value={notas} onChange={e => setNotas(e.target.value)} rows="3" /></label>
            </div>
          </fieldset>
          <button className="submit-order" disabled={!cartItems.length}><Check size={19} /> Confirmar pedido</button>
        </form>
      </div>
      <aside className="checkout-summary">
        <div className="panel-title"><div><p className="eyebrow">Resumo</p><h2>Itens</h2></div><CalendarClock size={22} /></div>
        {cartItems.length ? cartItems.map(i => <CartLine key={i.id} item={i} updateQuantity={updateQuantity} />) : (
          <div className="empty-state"><Trash2 size={30} /><p>O carrinho está vazio.</p></div>
        )}
        <Totals totals={totals} />
      </aside>
    </section>
  );
}

function ReadonlyField({ label, value }) {
  return (
    <label>{label}<input value={value || ""} readOnly className="readonly-field" /></label>
  );
}

function Confirmation({ order, onContinue }) {
  if (!order) return (
    <section className="confirmation"><ClipboardList size={42} /><h1>Nenhuma encomenda recente</h1>
      <button className="primary-action" onClick={onContinue}>Voltar à loja</button></section>
  );
  return (
    <section className="confirmation">
      <BadgeCheck size={48} />
      <p className="eyebrow">Pedido recebido</p>
      <h1>{order.id}</h1>
      <p>Estado <strong>{order.estado}</strong> · conclusão estimada <strong>{order.dataEstimadaConclusao}</strong>.</p>
      <div className="confirmation-grid">
        <span>{order.totals.totalCrates} grades</span>
        <span>{order.totals.totalLiters.toFixed(2)} litros</span>
        <span>{price(order.totals.total)}</span>
      </div>
      <button className="primary-action" onClick={onContinue}>Continuar compras</button>
    </section>
  );
}

function OrdersView({ backendOrders, onRefresh }) {
  return (
    <section className="orders-view">
      <div className="section-heading">
        <div><p className="eyebrow">Área do cliente</p><h1>Encomendas</h1></div>
        <button className="ghost-button" onClick={onRefresh}><RefreshCw size={15} /> Atualizar</button>
      </div>
      {backendOrders.length > 0 && (
        <p style={{ fontSize: 12, color: "#8C8480", marginBottom: 12 }}>
          {backendOrders.length} encomenda(s) associada(s) à sua conta.
        </p>
      )}
      {backendOrders.length ? (
        <div className="orders-list">
          {backendOrders.map((o, i) => {
            const tipoCliente = o.customer?.tipoCliente ?? "—";
            const nomeCliente = o.customer?.nome ?? "—";
            const totalValor  = o.totals?.total ?? o.totals?.totalValor ?? null;
            const totalGrades = o.totals?.totalGrades ?? null;
            const totalLitros = o.totals?.totalLiters ?? o.totals?.totalLitros ?? null;
            return (
              <article className="order-card" key={o.id ?? o.numeroPedido ?? i}>
                <div><strong>{o.numeroPedido ?? `PED-${String(o.id).padStart(3, "0")}`}</strong><span>{o.dataPedido} · {tipoCliente} · {nomeCliente}</span></div>
                <div><strong>{o.estado}</strong><span>Conclusão: {o.dataEstimadaConclusao ?? "—"}</span></div>
                <div>
                  <strong>{totalValor != null ? price(totalValor) : "—"}</strong>
                  <span>{totalGrades != null ? `${totalGrades} grades · ${Number(totalLitros ?? 0).toFixed(2)} L` : ""}</span>
                </div>
              </article>
            );
          })}
        </div>
      ) : (
        <div className="empty-orders"><ClipboardList size={44} /><h2>Sem encomendas registadas</h2><p>As encomendas confirmadas aparecem aqui.</p></div>
      )}
    </section>
  );
}
