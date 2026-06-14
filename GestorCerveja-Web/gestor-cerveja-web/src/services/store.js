const SESSION_KEY = "sessionCustomer";
const BASE        = "http://localhost:8080";

/** Litros por grade — alinhado com ReceitaDTO (11 L). */
export const CRATE_LITERS = 11;

// ── Sessão de cliente ─────────────────────────────────────────────────────────

export function getSessionCustomer() {
  try {
    const raw = localStorage.getItem(SESSION_KEY);
    return raw ? JSON.parse(raw) : null;
  } catch {
    return null;
  }
}

export function saveSessionCustomer(customer) {
  localStorage.setItem(SESSION_KEY, JSON.stringify(customer));
}

export function clearSessionCustomer() {
  localStorage.removeItem(SESSION_KEY);
}

/** Converte resposta da API para o formato usado no checkout. */
export function apiToCustomer(data) {
  return {
    idcliente:         data.idcliente,
    tipoCliente:       data.tipoCliente ?? "Particular",
    nomeCompleto:      data.nomeCompleto ?? "",
    nif:               data.nif ?? "",
    nomeEmpresa:       data.nomeEmpresa ?? "",
    vatEmpresa:        data.vatEmpresa ?? "",
    contactoPrincipal: data.contactoPrincipal ?? "",
    departamento:      data.departamento ?? "",
    email:             data.email ?? "",
    telefone:          data.telefone ?? "",
    morada:            data.morada ?? "",
    notas:             data.notas ?? "",
  };
}

// ── API de clientes ───────────────────────────────────────────────────────────

export async function registerCliente(data) {
  const res = await fetch(`${BASE}/api/clientes/register`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(data),
  });
  if (!res.ok) {
    const err = await res.json().catch(() => ({}));
    throw new Error(err.message || "Erro ao registar cliente.");
  }
  const profile = await res.json();
  const customer = apiToCustomer(profile);
  saveSessionCustomer(customer);
  return customer;
}

export async function fetchCliente(email) {
  const res = await fetch(`${BASE}/api/clientes/me?email=${encodeURIComponent(email)}`);
  if (res.status === 404) throw new Error("Cliente não encontrado. Registe-se primeiro.");
  if (!res.ok) {
    const err = await res.json().catch(() => ({}));
    throw new Error(err.message || "Erro ao identificar cliente.");
  }
  const profile = await res.json();
  const customer = apiToCustomer(profile);
  saveSessionCustomer(customer);
  return customer;
}

// ── Catálogo e pedidos ────────────────────────────────────────────────────────

export async function fetchRecipes() {
  const res = await fetch(`${BASE}/api/receitas`);
  if (!res.ok) throw new Error("Erro ao carregar receitas.");
  return res.json();
}

/** Mapeia ReceitaDTO para o formato usado na loja. */
export function mapRecipe(r) {
  return {
    id:            r.id,
    name:          r.nome,
    description:   r.descricao ?? "",
    pricePerLiter: r.precoPorLitro,
    pricePerGrade: r.precoGrade,
  };
}

export async function submitOrderToBackend(order) {
  const res = await fetch(`${BASE}/api/pedidos`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({
      dataEstimadaConclusao: order.dataEstimadaConclusao,
      customer: {
        type:    order.customer.tipoCliente,
        name:    order.customer.nomeCompleto || order.customer.contactoPrincipal,
        email:   order.customer.email,
        phone:   order.customer.telefone,
        nif:     order.customer.nif || order.customer.vatEmpresa,
        empresa: order.customer.nomeEmpresa,
      },
      items: order.items.map(item => ({
        idreceita:        item.id,
        quantidadeLitros: item.liters,
        quantidadeGrades: item.quantity,
        precoUnitario:    item.subtotal / item.liters,
      })),
    }),
  });
  if (!res.ok) {
    const err = await res.json().catch(() => ({}));
    throw new Error(err.message || "Erro ao enviar pedido");
  }
  return res.json();
}

export async function fetchOrders(email = null) {
  const url = email
    ? `${BASE}/api/pedidos?email=${encodeURIComponent(email)}`
    : `${BASE}/api/pedidos`;
  const res = await fetch(url);
  if (!res.ok) throw new Error("Erro ao carregar encomendas.");
  return res.json();
}

// ── Admin ─────────────────────────────────────────────────────────────────────

function authHeaders(creds) {
  return { Authorization: `Basic ${btoa(`${creds.nome}:${creds.senha}`)}`, "Content-Type": "application/json" };
}

export async function adminLogin(nome, senha) {
  const res = await fetch(`${BASE}/api/admin/stats`, { headers: authHeaders({ nome, senha }) });
  if (res.status === 401) throw new Error("Credenciais inválidas.");
  if (!res.ok) throw new Error("Erro ao autenticar.");
  return res.json();
}

export async function adminFetch(path, creds, options = {}) {
  const res = await fetch(`${BASE}/api/admin${path}`, { ...options, headers: { ...authHeaders(creds), ...options.headers } });
  if (!res.ok) {
    const err = await res.json().catch(() => ({}));
    throw new Error(err.message || `Erro ${res.status}`);
  }
  if (res.status === 204) return null;
  return res.json();
}
