import React, { useState } from "react";
import { Beer, Building2, LogIn, User } from "lucide-react";
import { fetchCliente, registerCliente } from "../services/store.js";

export default function ClienteAuth({ onAuthenticated }) {
  const [step, setStep]           = useState("tipo"); // "tipo" | "form" | "login"
  const [tipoCliente, setTipo]    = useState("Particular");
  const [form, setForm]           = useState({
    nomeCompleto: "", nif: "", email: "", telefone: "",
    nomeEmpresa: "", vatEmpresa: "", contactoPrincipal: "",
  });
  const [loginEmail, setLoginEmail] = useState("");
  const [error, setError]         = useState("");
  const [busy, setBusy]           = useState(false);

  function handleChange(e) {
    const { name, value } = e.target;
    setForm(f => ({ ...f, [name]: value }));
  }

  async function handleRegister(e) {
    e.preventDefault();
    setBusy(true);
    setError("");
    try {
      const payload = {
        tipoCliente,
        email:    form.email.trim(),
        telefone: form.telefone.trim(),
      };
      if (tipoCliente === "Revendedor") {
        payload.empresa           = form.nomeEmpresa.trim();
        payload.vat               = form.vatEmpresa.trim();
        payload.contactoPrincipal = form.contactoPrincipal.trim();
      } else {
        payload.nome = form.nomeCompleto.trim();
        payload.nif  = form.nif.trim();
      }
      const customer = await registerCliente(payload);
      onAuthenticated(customer);
    } catch (err) {
      setError(err.message);
    } finally {
      setBusy(false);
    }
  }

  async function handleLogin(e) {
    e.preventDefault();
    setBusy(true);
    setError("");
    try {
      const customer = await fetchCliente(loginEmail.trim());
      onAuthenticated(customer);
    } catch (err) {
      setError(err.message);
    } finally {
      setBusy(false);
    }
  }

  return (
    <div className="auth-screen">
      <div className="auth-card">
        <div className="auth-header">
          <span className="brand-mark"><Beer size={28} /></span>
          <div>
            <strong>Neo Cerveja</strong>
            <p>Identifique-se para aceder à loja</p>
          </div>
        </div>

        {step === "tipo" && (
          <>
            <p className="auth-subtitle">Como pretende encomendar?</p>
            <div className="client-toggle">
              <button
                type="button"
                className={tipoCliente === "Particular" ? "checked" : ""}
                onClick={() => setTipo("Particular")}
              >
                <User size={20} /> Particular
              </button>
              <button
                type="button"
                className={tipoCliente === "Revendedor" ? "checked" : ""}
                onClick={() => setTipo("Revendedor")}
              >
                <Building2 size={20} /> Revendedor
              </button>
            </div>
            <button className="primary-action auth-continue" onClick={() => setStep("form")}>
              Continuar
            </button>
            <button className="ghost-button auth-alt" onClick={() => setStep("login")}>
              <LogIn size={16} /> Já tenho conta
            </button>
          </>
        )}

        {step === "form" && (
          <form className="auth-form" onSubmit={handleRegister}>
            <button type="button" className="ghost-button auth-back" onClick={() => setStep("tipo")}>
              ← Voltar
            </button>
            <p className="auth-subtitle">
              {tipoCliente === "Revendedor" ? "Dados da empresa" : "Dados pessoais"}
            </p>
            {tipoCliente === "Revendedor" ? (
              <>
                <label>Empresa<input name="nomeEmpresa" value={form.nomeEmpresa} onChange={handleChange} required /></label>
                <label>VAT / NIF<input name="vatEmpresa" value={form.vatEmpresa} onChange={handleChange} required /></label>
                <label>Contacto principal<input name="contactoPrincipal" value={form.contactoPrincipal} onChange={handleChange} required /></label>
              </>
            ) : (
              <>
                <label>Nome completo<input name="nomeCompleto" value={form.nomeCompleto} onChange={handleChange} required /></label>
                <label>NIF<input name="nif" value={form.nif} onChange={handleChange} required /></label>
              </>
            )}
            <label>Email<input name="email" type="email" value={form.email} onChange={handleChange} required /></label>
            <label>Telefone<input name="telefone" value={form.telefone} onChange={handleChange} required /></label>
            {error && <p className="auth-error">{error}</p>}
            <button type="submit" className="primary-action" disabled={busy}>
              {busy ? "A registar…" : "Entrar na loja"}
            </button>
          </form>
        )}

        {step === "login" && (
          <form className="auth-form" onSubmit={handleLogin}>
            <button type="button" className="ghost-button auth-back" onClick={() => setStep("tipo")}>
              ← Voltar
            </button>
            <p className="auth-subtitle">Identifique-se pelo email</p>
            <label>Email<input type="email" value={loginEmail} onChange={e => setLoginEmail(e.target.value)} required placeholder="o seu email de registo" /></label>
            {error && <p className="auth-error">{error}</p>}
            <button type="submit" className="primary-action" disabled={busy}>
              {busy ? "A identificar…" : "Entrar"}
            </button>
          </form>
        )}
      </div>
    </div>
  );
}
