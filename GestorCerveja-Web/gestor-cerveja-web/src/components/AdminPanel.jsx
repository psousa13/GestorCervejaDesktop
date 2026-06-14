import React, { useState, useEffect, useCallback } from "react";
import {
  BarChart3, Beer, ChevronLeft, ClipboardList, LogOut,
  Package, RefreshCw, Shield, Trash2, Users, Wheat, AlertTriangle
} from "lucide-react";
import { adminLogin, adminFetch } from "../services/store.js";

// ── Palette tokens (inline — sem depender do styles.css da loja) ───────────────
const wine  = "#7B2D3E";
const bg    = "#FAF8F6";
const white = "#fff";
const border = "#E8E2DF";

// ── Helpers ───────────────────────────────────────────────────────────────────
const fmt = new Intl.NumberFormat("pt-PT", { style: "currency", currency: "EUR" });
const price = v => fmt.format(v ?? 0);

function estadoBadge(estado) {
  const map = {
    Pendente:      { bg: "#FAEEDA", color: "#854F0B" },
    "Em Produção": { bg: "#E6F1FB", color: "#185FA5" },
    Concluído:     { bg: "#EAF3DE", color: "#3B6D11" },
    Cancelado:     { bg: "#FCEBEB", color: "#A32D2D" },
  };
  const s = map[estado] ?? { bg: "#F0EBE8", color: "#555" };
  return (
    <span style={{ background: s.bg, color: s.color, borderRadius: 20, padding: "2px 10px", fontSize: 11, fontWeight: 600 }}>
      {estado ?? "—"}
    </span>
  );
}

// ═════════════════════════════════════════════════════════════════════════════
// MAIN EXPORT
// ═════════════════════════════════════════════════════════════════════════════
export default function AdminPanel({ onBack }) {
  const [creds, setCreds] = useState(null); // { nome, senha }

  if (!creds) return <AdminLogin onLogin={setCreds} onBack={onBack} />;
  return <AdminDashboard creds={creds} onLogout={() => setCreds(null)} />;
}

// ── Login ─────────────────────────────────────────────────────────────────────
function AdminLogin({ onLogin, onBack }) {
  const [nome, setNome]   = useState("");
  const [senha, setSenha] = useState("");
  const [error, setError] = useState("");
  const [busy, setBusy]   = useState(false);

  async function handleSubmit(e) {
    e.preventDefault();
    setBusy(true); setError("");
    try {
      await adminLogin(nome, senha);
      onLogin({ nome, senha });
    } catch (err) {
      setError(err.message);
    } finally {
      setBusy(false);
    }
  }

  return (
    <div style={{ minHeight: "100vh", background: bg, display: "flex", alignItems: "center", justifyContent: "center" }}>
      <div style={{ background: white, border: `1px solid ${border}`, borderRadius: 14, padding: "40px 36px", width: 360 }}>
        <div style={{ display: "flex", alignItems: "center", gap: 10, marginBottom: 24 }}>
          <Shield size={26} color={wine} />
          <div>
            <strong style={{ fontSize: 18, color: "#1A1614" }}>Área de Administração</strong>
            <p style={{ fontSize: 12, color: "#8C8480", margin: 0 }}>Acesso restrito</p>
          </div>
        </div>
        <form onSubmit={handleSubmit} style={{ display: "flex", flexDirection: "column", gap: 12 }}>
          <input placeholder="Nome de utilizador" value={nome} onChange={e => setNome(e.target.value)}
            required style={inputStyle} />
          <input placeholder="Senha" type="password" value={senha} onChange={e => setSenha(e.target.value)}
            required style={inputStyle} />
          {error && <p style={{ color: "#A32D2D", fontSize: 12, margin: 0 }}>{error}</p>}
          <button type="submit" disabled={busy}
            style={{ ...btnPrimary, opacity: busy ? 0.7 : 1 }}>
            {busy ? "A autenticar…" : "Entrar"}
          </button>
        </form>
        <button onClick={onBack} style={{ ...btnGhost, marginTop: 12, width: "100%" }}>
          <ChevronLeft size={15} /> Voltar à loja
        </button>
      </div>
    </div>
  );
}

// ── Main Dashboard ─────────────────────────────────────────────────────────────
function AdminDashboard({ creds, onLogout }) {
  const [tab, setTab] = useState("dashboard");

  const tabs = [
    { id: "dashboard",     icon: <BarChart3 size={16} />,   label: "Dashboard" },
    { id: "pedidos",       icon: <ClipboardList size={16} />,label: "Pedidos" },
    { id: "receitas",      icon: <Beer size={16} />,         label: "Receitas" },
    { id: "clientes",      icon: <Users size={16} />,        label: "Clientes" },
    { id: "ingredientes",  icon: <Wheat size={16} />,        label: "Stock" },
    { id: "utilizadores",  icon: <Shield size={16} />,       label: "Utilizadores" },
  ];

  return (
    <div style={{ display: "flex", minHeight: "100vh", background: bg }}>
      {/* Sidebar */}
      <nav style={{ width: 210, background: "#3E1520", display: "flex", flexDirection: "column", padding: "20px 0" }}>
        <div style={{ padding: "0 18px 18px", borderBottom: "1px solid rgba(255,255,255,0.08)" }}>
          <strong style={{ color: white, fontSize: 15 }}>Neo Cerveja</strong>
          <p style={{ color: "rgba(255,255,255,0.35)", fontSize: 10, margin: "2px 0 0" }}>Admin — {creds.nome}</p>
        </div>
        <div style={{ flex: 1, paddingTop: 10 }}>
          {tabs.map(t => (
            <button key={t.id} onClick={() => setTab(t.id)}
              style={{ display: "flex", alignItems: "center", gap: 8, width: "calc(100% - 14px)", margin: "1px 7px",
                padding: "9px 10px", borderRadius: 6, border: "none", cursor: "pointer", fontSize: 12.5, textAlign: "left",
                background: tab === t.id ? "rgba(255,255,255,0.12)" : "transparent",
                color: tab === t.id ? white : "rgba(255,255,255,0.5)" }}>
              {t.icon} {t.label}
            </button>
          ))}
        </div>
        <button onClick={onLogout}
          style={{ display: "flex", alignItems: "center", gap: 8, margin: "0 7px", padding: "9px 10px",
            borderRadius: 6, border: "none", cursor: "pointer", background: "transparent",
            color: "rgba(255,255,255,0.4)", fontSize: 12 }}>
          <LogOut size={15} /> Terminar sessão
        </button>
      </nav>

      {/* Content */}
      <main style={{ flex: 1, overflow: "auto" }}>
        {tab === "dashboard"    && <StatsDashboard creds={creds} />}
        {tab === "pedidos"      && <PedidosTab creds={creds} />}
        {tab === "receitas"     && <ReceitasTab creds={creds} />}
        {tab === "clientes"     && <ClientesTab creds={creds} />}
        {tab === "ingredientes" && <IngredientesTab creds={creds} />}
        {tab === "utilizadores" && <UtilizadoresTab creds={creds} />}
      </main>
    </div>
  );
}

// ── Stats Dashboard ────────────────────────────────────────────────────────────
function StatsDashboard({ creds }) {
  const [stats, setStats] = useState(null);
  const [error, setError] = useState("");

  useEffect(() => {
    adminFetch("/stats", creds)
      .then(setStats)
      .catch(e => setError(e.message));
  }, []);

  const cards = stats ? [
    { label: "Total Pedidos",    value: stats.totalPedidos,      color: "#185FA5", sub: "em sistema" },
    { label: "Clientes",         value: stats.totalClientes,     color: wine,      sub: "registados" },
    { label: "Receitas",         value: stats.totalReceitas,     color: "#3B6D11", sub: "disponíveis" },
    { label: "Stock Crítico",    value: stats.stockCritico,      color: "#A32D2D", sub: "ingredientes" },
    { label: "Pedidos Pendentes",value: stats.pedidosPendentes,  color: "#854F0B", sub: "por processar" },
    { label: "Total Faturado",   value: price(stats.totalFaturado), color: wine,  sub: "acumulado" },
  ] : [];

  return (
    <section style={contentPad}>
      <PageHeader title="Dashboard" subtitle="Resumo geral do sistema" />
      {error && <ErrorBanner msg={error} />}
      {!stats && !error && <Loading />}
      <div style={{ display: "grid", gridTemplateColumns: "repeat(auto-fill,minmax(200px,1fr))", gap: 14 }}>
        {cards.map(c => (
          <div key={c.label} style={{ background: white, border: `1px solid ${border}`, borderRadius: 10, padding: 16 }}>
            <p style={{ fontSize: 10.5, color: "#8C8480", margin: "0 0 4px" }}>{c.label}</p>
            <p style={{ fontSize: 26, fontWeight: 700, color: "#1A1614", margin: "0 0 4px" }}>{c.value}</p>
            <span style={{ fontSize: 10, background: "#F0EBE8", color: "#555", borderRadius: 20, padding: "2px 8px" }}>{c.sub}</span>
          </div>
        ))}
      </div>
    </section>
  );
}

// ── Pedidos ────────────────────────────────────────────────────────────────────
function PedidosTab({ creds }) {
  const [pedidos, setPedidos]   = useState([]);
  const [error, setError]       = useState("");
  const [updating, setUpdating] = useState(null);

  const load = useCallback(() => {
    adminFetch("/pedidos", creds).then(setPedidos).catch(e => setError(e.message));
  }, [creds]);
  useEffect(load, [load]);

  async function changeEstado(id, estado) {
    setUpdating(id);
    try {
      await adminFetch(`/pedidos/${id}/estado`, creds, {
        method: "PUT",
        body: JSON.stringify({ estado }),
      });
      load();
    } catch (e) { setError(e.message); } finally { setUpdating(null); }
  }

  async function deletePedido(id) {
    if (!confirm("Apagar pedido #" + id + "?")) return;
    try { await adminFetch(`/pedidos/${id}`, creds, { method: "DELETE" }); load(); }
    catch (e) { setError(e.message); }
  }

  const estados = ["Pendente", "Em Produção", "Concluído", "Cancelado"];

  return (
    <section style={contentPad}>
      <PageHeader title="Pedidos" subtitle={`${pedidos.length} registados`} onRefresh={load} />
      {error && <ErrorBanner msg={error} />}
      <table style={tableStyle}>
        <thead><tr style={{ background: "#F5F0EE" }}>
          {["Nº Pedido","Cliente ID","Data","Estado","Estimativa","Ações"].map(h => <th key={h} style={thStyle}>{h}</th>)}
        </tr></thead>
        <tbody>
          {pedidos.map(p => (
            <tr key={p.id} style={trStyle}>
              <td style={tdStyle}><strong style={{ color: wine }}>PED-{String(p.id).padStart(3,"0")}</strong></td>
              <td style={tdStyle}>{p.idcliente}</td>
              <td style={tdStyle}>{p.dataPedido}</td>
              <td style={tdStyle}>
                <select value={p.estado ?? "Pendente"} disabled={updating === p.id}
                  onChange={e => changeEstado(p.id, e.target.value)} style={selectStyle}>
                  {estados.map(e => <option key={e}>{e}</option>)}
                </select>
              </td>
              <td style={tdStyle}>{p.dataEstimadaConclusao ?? "—"}</td>
              <td style={tdStyle}>
                <button onClick={() => deletePedido(p.id)} style={btnDanger} title="Apagar">
                  <Trash2 size={14} />
                </button>
              </td>
            </tr>
          ))}
          {!pedidos.length && <tr><td colSpan={6} style={{ textAlign: "center", color: "#8C8480", padding: 28 }}>Sem pedidos.</td></tr>}
        </tbody>
      </table>
    </section>
  );
}

// ── Receitas ───────────────────────────────────────────────────────────────────
function ReceitasTab({ creds }) {
  const [receitas, setReceitas] = useState([]);
  const [error, setError]       = useState("");
  const load = useCallback(() => { adminFetch("/receitas", creds).then(setReceitas).catch(e => setError(e.message)); }, [creds]);
  useEffect(load, [load]);

  return (
    <section style={contentPad}>
      <PageHeader title="Receitas" subtitle={`${receitas.length} disponíveis`} onRefresh={load} />
      {error && <ErrorBanner msg={error} />}
      <table style={tableStyle}>
        <thead><tr style={{ background: "#F5F0EE" }}>
          {["ID","Nome","Descrição"].map(h => <th key={h} style={thStyle}>{h}</th>)}
        </tr></thead>
        <tbody>
          {receitas.map(r => (
            <tr key={r.id} style={trStyle}>
              <td style={tdStyle}><span style={{ color: "#8C8480" }}>#{r.id}</span></td>
              <td style={tdStyle}><strong>{r.nome}</strong></td>
              <td style={tdStyle}>{r.descricao ?? "—"}</td>
            </tr>
          ))}
          {!receitas.length && <tr><td colSpan={3} style={{ textAlign: "center", color: "#8C8480", padding: 28 }}>Sem receitas.</td></tr>}
        </tbody>
      </table>
    </section>
  );
}

// ── Clientes ───────────────────────────────────────────────────────────────────
function ClientesTab({ creds }) {
  const [clientes, setClientes] = useState([]);
  const [error, setError]       = useState("");
  const load = useCallback(() => { adminFetch("/clientes", creds).then(setClientes).catch(e => setError(e.message)); }, [creds]);
  useEffect(load, [load]);

  return (
    <section style={contentPad}>
      <PageHeader title="Clientes" subtitle={`${clientes.length} registados`} onRefresh={load} />
      {error && <ErrorBanner msg={error} />}
      <table style={tableStyle}>
        <thead><tr style={{ background: "#F5F0EE" }}>
          {["ID","Tipo","Email","Telefone","Registo"].map(h => <th key={h} style={thStyle}>{h}</th>)}
        </tr></thead>
        <tbody>
          {clientes.map(c => (
            <tr key={c.id} style={trStyle}>
              <td style={tdStyle}><span style={{ color: "#8C8480" }}>#{c.id}</span></td>
              <td style={tdStyle}>{estadoBadge(c.tipoCliente)}</td>
              <td style={tdStyle}>{c.email ?? "—"}</td>
              <td style={tdStyle}>{c.telefone ?? "—"}</td>
              <td style={tdStyle}>{c.dataRegisto}</td>
            </tr>
          ))}
          {!clientes.length && <tr><td colSpan={5} style={{ textAlign: "center", color: "#8C8480", padding: 28 }}>Sem clientes.</td></tr>}
        </tbody>
      </table>
    </section>
  );
}

// ── Stock Crítico ──────────────────────────────────────────────────────────────
function IngredientesTab({ creds }) {
  const [items, setItems] = useState([]);
  const [error, setError] = useState("");
  const load = useCallback(() => { adminFetch("/ingredientes/criticos", creds).then(setItems).catch(e => setError(e.message)); }, [creds]);
  useEffect(load, [load]);

  return (
    <section style={contentPad}>
      <PageHeader title="Stock Crítico" subtitle="Ingredientes abaixo do mínimo" onRefresh={load} />
      {error && <ErrorBanner msg={error} />}
      {items.length > 0 && (
        <div style={{ display: "flex", alignItems: "center", gap: 8, background: "#FAEEDA", border: "1px solid #FAC775",
          borderRadius: 8, padding: "10px 14px", marginBottom: 16, fontSize: 13, color: "#854F0B" }}>
          <AlertTriangle size={16} /> {items.length} ingrediente(s) com stock crítico.
        </div>
      )}
      <table style={tableStyle}>
        <thead><tr style={{ background: "#F5F0EE" }}>
          {["Nome","Unidade","Stock Atual","Stock Mínimo","Estado"].map(h => <th key={h} style={thStyle}>{h}</th>)}
        </tr></thead>
        <tbody>
          {items.map(i => (
            <tr key={i.id} style={trStyle}>
              <td style={tdStyle}><strong>{i.nome}</strong></td>
              <td style={tdStyle}>{i.unidade ?? "—"}</td>
              <td style={{ ...tdStyle, color: "#A32D2D", fontWeight: 600 }}>{i.stockAtual}</td>
              <td style={tdStyle}>{i.stockMinimo}</td>
              <td style={tdStyle}>{estadoBadge("Cancelado")}</td>
            </tr>
          ))}
          {!items.length && (
            <tr><td colSpan={5} style={{ textAlign: "center", color: "#3B6D11", padding: 28 }}>
              ✓ Todos os ingredientes com stock suficiente.
            </td></tr>
          )}
        </tbody>
      </table>
    </section>
  );
}

// ── Utilizadores ──────────────────────────────────────────────────────────────
function UtilizadoresTab({ creds }) {
  const [users, setUsers]     = useState([]);
  const [roles, setRoles]     = useState([]);
  const [error, setError]     = useState("");
  const [form, setForm]       = useState({ nome: "", senha: "", idrole: 1 });
  const [saving, setSaving]   = useState(false);
  const [success, setSuccess] = useState("");

  const load = useCallback(() => {
    adminFetch("/utilizadores", creds).then(setUsers).catch(e => setError(e.message));
    adminFetch("/roles", creds).then(setRoles).catch(() => {});
  }, [creds]);
  useEffect(load, [load]);

  async function deleteUser(id) {
    if (!confirm("Apagar utilizador #" + id + "?")) return;
    try { await adminFetch(`/utilizadores/${id}`, creds, { method: "DELETE" }); load(); }
    catch (e) { setError(e.message); }
  }

  async function handleCreate(e) {
    e.preventDefault(); setSaving(true); setError(""); setSuccess("");
    try {
      await adminFetch("/utilizadores", creds, { method: "POST", body: JSON.stringify(form) });
      setSuccess("Utilizador criado com sucesso.");
      setForm({ nome: "", senha: "", idrole: 1 });
      load();
    } catch (err) { setError(err.message); } finally { setSaving(false); }
  }

  return (
    <section style={contentPad}>
      <PageHeader title="Utilizadores" subtitle={`${users.length} registados`} onRefresh={load} />
      {error   && <ErrorBanner msg={error} />}
      {success && <div style={{ background: "#EAF3DE", color: "#3B6D11", borderRadius: 8, padding: "10px 14px", marginBottom: 14, fontSize: 13 }}>{success}</div>}

      {/* Novo utilizador */}
      <div style={{ background: white, border: `1px solid ${border}`, borderRadius: 10, padding: 20, marginBottom: 20 }}>
        <p style={{ fontWeight: 600, marginBottom: 12, fontSize: 14 }}>Novo Utilizador</p>
        <form onSubmit={handleCreate} style={{ display: "flex", gap: 10, flexWrap: "wrap" }}>
          <input placeholder="Nome" value={form.nome} onChange={e => setForm(f => ({...f, nome: e.target.value}))}
            required style={{ ...inputStyle, flex: "1 1 140px" }} />
          <input placeholder="Senha" type="password" value={form.senha} onChange={e => setForm(f => ({...f, senha: e.target.value}))}
            required style={{ ...inputStyle, flex: "1 1 140px" }} />
          <select value={form.idrole} onChange={e => setForm(f => ({...f, idrole: Number(e.target.value)}))} style={{ ...selectStyle, flex: "1 1 140px" }}>
            {roles.map(r => <option key={r.id} value={r.id}>{r.nome}</option>)}
            {!roles.length && <option value={1}>Role 1</option>}
          </select>
          <button type="submit" disabled={saving} style={{ ...btnPrimary, flex: "0 0 auto" }}>
            {saving ? "A criar…" : "+ Criar"}
          </button>
        </form>
      </div>

      <table style={tableStyle}>
        <thead><tr style={{ background: "#F5F0EE" }}>
          {["ID","Nome","Role","Ações"].map(h => <th key={h} style={thStyle}>{h}</th>)}
        </tr></thead>
        <tbody>
          {users.map(u => (
            <tr key={u.id} style={trStyle}>
              <td style={tdStyle}><span style={{ color: "#8C8480" }}>#{u.id}</span></td>
              <td style={tdStyle}><strong>{u.nome}</strong></td>
              <td style={tdStyle}>{u.idrole}</td>
              <td style={tdStyle}>
                <button onClick={() => deleteUser(u.id)} style={btnDanger} title="Apagar">
                  <Trash2 size={14} />
                </button>
              </td>
            </tr>
          ))}
          {!users.length && <tr><td colSpan={4} style={{ textAlign: "center", color: "#8C8480", padding: 28 }}>Sem utilizadores.</td></tr>}
        </tbody>
      </table>
    </section>
  );
}

// ── Shared sub-components ──────────────────────────────────────────────────────
function PageHeader({ title, subtitle, onRefresh }) {
  return (
    <div style={{ display: "flex", alignItems: "flex-start", justifyContent: "space-between", marginBottom: 20 }}>
      <div>
        <p style={{ fontSize: 10.5, color: "#8C8480", margin: 0, textTransform: "uppercase", letterSpacing: 1 }}>Administração</p>
        <h2 style={{ margin: "4px 0 2px", fontSize: 22, color: "#1A1614" }}>{title}</h2>
        {subtitle && <p style={{ margin: 0, fontSize: 13, color: "#8C8480" }}>{subtitle}</p>}
      </div>
      {onRefresh && (
        <button onClick={onRefresh} style={btnGhost} title="Recarregar">
          <RefreshCw size={15} />
        </button>
      )}
    </div>
  );
}
function ErrorBanner({ msg }) {
  return <div style={{ background: "#FCEBEB", color: "#A32D2D", borderRadius: 8, padding: "10px 14px", marginBottom: 14, fontSize: 13 }}>{msg}</div>;
}
function Loading() {
  return <p style={{ color: "#8C8480", fontSize: 13, padding: 20 }}>A carregar…</p>;
}

// ── Style tokens (inline JS objects) ──────────────────────────────────────────
const contentPad = { padding: "28px 32px" };
const inputStyle = {
  border: `1px solid ${border}`, borderRadius: 7, padding: "8px 12px",
  fontSize: 13, background: "#F5F2F0", outline: "none"
};
const btnPrimary = {
  background: wine, color: white, border: "none", borderRadius: 7,
  padding: "8px 16px", fontSize: 13, fontWeight: 600, cursor: "pointer"
};
const btnGhost = {
  background: "transparent", color: "#555", border: `1px solid ${border}`, borderRadius: 7,
  padding: "7px 12px", fontSize: 12, cursor: "pointer", display: "flex", alignItems: "center", gap: 5
};
const btnDanger = {
  background: "white", color: "#A32D2D", border: "1px solid #F5CECE", borderRadius: 6,
  padding: "4px 8px", cursor: "pointer", display: "flex", alignItems: "center"
};
const selectStyle = {
  border: `1px solid ${border}`, borderRadius: 7, padding: "6px 10px", fontSize: 12,
  background: white, cursor: "pointer"
};
const tableStyle = { width: "100%", borderCollapse: "collapse", background: white, border: `1px solid ${border}`, borderRadius: 10, overflow: "hidden", fontSize: 13 };
const thStyle    = { textAlign: "left", padding: "10px 14px", fontSize: 11, fontWeight: 600, color: "#555", borderBottom: `1px solid ${border}` };
const tdStyle    = { padding: "10px 14px", borderBottom: `1px solid #F5F0EE}`, verticalAlign: "middle" };
const trStyle    = { transition: "background 0.1s" };
