import { useEffect, useMemo, useState } from "react";
import "./App.css";
import api from "./services/api";

const resources = {
  dashboard: { label: "Dashboard" },
  clientes: {
    label: "Clientes",
    endpoint: "/clientes",
    idKey: "idCliente",
    emptyForm: { nome: "", telefone: "", email: "", endereco: "" },
    columns: [
      { key: "nome", label: "Nome" },
      { key: "telefone", label: "Telefone" },
      { key: "email", label: "Email" },
      { key: "endereco", label: "Endereco" },
    ],
    fields: [
      { name: "nome", label: "Nome do cliente", required: true },
      { name: "telefone", label: "Telefone" },
      { name: "email", label: "E-mail", type: "email" },
      { name: "endereco", label: "Endereco" },
    ],
  },
  categorias: {
    label: "Categorias",
    endpoint: "/categorias",
    idKey: "idCategoria",
    emptyForm: { nome: "" },
    columns: [{ key: "nome", label: "Nome" }],
    fields: [
      {
        name: "nome",
        label: "Nome da categoria",
        type: "select",
        required: true,
        options: ["Bebidas", "Pratos principais", "Sobremesas"],
      },
    ],
  },
  produtos: {
    label: "Produtos",
    endpoint: "/produtos",
    idKey: "idProduto",
    emptyForm: { nome: "", preco: "", estoque: "", categoriaId: "" },
    columns: [
      { key: "nome", label: "Nome" },
      { key: "preco", label: "Preco", format: formatCurrency },
      { key: "estoque", label: "Estoque" },
      { key: "categoria.nome", label: "Categoria" },
    ],
    fields: [
      { name: "nome", label: "Nome do produto", required: true },
      { name: "preco", label: "Preco", type: "number", step: "0.01", required: true },
      { name: "estoque", label: "Estoque", type: "number", required: true },
      { name: "categoriaId", label: "Categoria", type: "select", source: "categorias", required: true },
    ],
    toForm: (item) => ({
      nome: item.nome ?? "",
      preco: item.preco ?? "",
      estoque: item.estoque ?? "",
      categoriaId: item.categoria?.idCategoria ?? "",
    }),
    toPayload: (form) => ({
      nome: form.nome,
      preco: Number(form.preco),
      estoque: Number(form.estoque),
      categoria: { idCategoria: Number(form.categoriaId) },
    }),
  },
  mesas: {
    label: "Mesas",
    endpoint: "/mesas",
    idKey: "idMesa",
    emptyForm: { numero: "", capacidade: "", status: "Livre" },
    columns: [
      { key: "numero", label: "Numero" },
      { key: "capacidade", label: "Capacidade" },
      { key: "status", label: "Status" },
    ],
    fields: [
      { name: "numero", label: "Numero", type: "number", required: true },
      { name: "capacidade", label: "Capacidade", type: "number", required: true },
      { name: "status", label: "Status", required: true },
    ],
    toPayload: (form) => ({
      numero: Number(form.numero),
      capacidade: Number(form.capacidade),
      status: form.status,
    }),
  },
  funcionarios: {
    label: "Funcionarios",
    endpoint: "/funcionarios",
    idKey: "idFuncionario",
    emptyForm: { nome: "", cargo: "", salario: "" },
    columns: [
      { key: "nome", label: "Nome" },
      { key: "cargo", label: "Cargo" },
      { key: "salario", label: "Salario", format: formatCurrency },
    ],
    fields: [
      { name: "nome", label: "Nome do funcionario", required: true },
      {
        name: "cargo",
        label: "Cargo",
        type: "select",
        required: true,
        options: ["Atendente", "Garçom", "Responsável", "Funcionário responsável"],
      },
      { name: "salario", label: "Salario", type: "number", step: "0.01", required: true },
    ],
    toPayload: (form) => ({
      nome: form.nome,
      cargo: form.cargo,
      salario: Number(form.salario),
    }),
  },
  pedidos: {
    label: "Pedidos",
    endpoint: "/pedidos",
    idKey: "idPedido",
    emptyForm: { clienteId: "", funcionarioId: "", mesaId: "", dataPedido: "", status: "Aberto" },
    columns: [
      { key: "cliente.nome", label: "Cliente" },
      { key: "funcionario.nome", label: "Funcionario" },
      { key: "mesa.numero", label: "Mesa" },
      { key: "dataPedido", label: "Data", format: formatDateTime },
      { key: "status", label: "Status" },
    ],
    fields: [
      { name: "clienteId", label: "Cliente", type: "select", source: "clientes", required: true },
      { name: "funcionarioId", label: "Funcionario", type: "select", source: "funcionarios", required: true },
      { name: "mesaId", label: "Mesa", type: "select", source: "mesas", required: true },
      { name: "dataPedido", label: "Data do pedido", type: "datetime-local", required: true },
      { name: "status", label: "Status", required: true },
    ],
    toForm: (item) => ({
      clienteId: item.cliente?.idCliente ?? "",
      funcionarioId: item.funcionario?.idFuncionario ?? "",
      mesaId: item.mesa?.idMesa ?? "",
      dataPedido: toDateTimeInput(item.dataPedido),
      status: item.status ?? "",
    }),
    toPayload: (form) => ({
      cliente: { idCliente: Number(form.clienteId) },
      funcionario: { idFuncionario: Number(form.funcionarioId) },
      mesa: { idMesa: Number(form.mesaId) },
      dataPedido: form.dataPedido,
      status: form.status,
    }),
  },
  pagamentos: {
    label: "Pagamentos",
    endpoint: "/pagamentos",
    idKey: "idPagamento",
    emptyForm: { pedidoId: "", formaPagamento: "", valorPago: "", dataPagamento: "", status: "Pendente" },
    columns: [
      { key: "pedido.idPedido", label: "Pedido" },
      { key: "formaPagamento", label: "Forma" },
      { key: "valorPago", label: "Valor", format: formatCurrency },
      { key: "dataPagamento", label: "Data", format: formatDateTime },
      { key: "status", label: "Status" },
    ],
    fields: [
      { name: "pedidoId", label: "Pedido", type: "select", source: "pedidos", required: true },
      { name: "formaPagamento", label: "Forma de pagamento", required: true },
      { name: "valorPago", label: "Valor pago", type: "number", step: "0.01", required: true },
      { name: "dataPagamento", label: "Data do pagamento", type: "datetime-local", required: true },
      {
        name: "status",
        label: "Status",
        type: "select",
        required: true,
        options: ["Pendente", "Pago"],
      },
    ],
    toForm: (item) => ({
      pedidoId: item.pedido?.idPedido ?? "",
      formaPagamento: item.formaPagamento ?? "",
      valorPago: item.valorPago ?? "",
      dataPagamento: toDateTimeInput(item.dataPagamento),
      status: item.status ?? "",
    }),
    toPayload: (form) => ({
      pedido: { idPedido: Number(form.pedidoId) },
      formaPagamento: form.formaPagamento,
      valorPago: Number(form.valorPago),
      dataPagamento: form.dataPagamento,
      status: form.status,
    }),
  },
};

const menuItems = ["dashboard", "clientes", "categorias", "produtos", "mesas", "funcionarios", "pedidos", "pagamentos"];

function App() {
  const [activeResource, setActiveResource] = useState("dashboard");
  const [data, setData] = useState({});
  const [forms, setForms] = useState(createInitialForms);
  const [editingIds, setEditingIds] = useState({});
  const [loading, setLoading] = useState(true);
  const [saving, setSaving] = useState(false);
  const [error, setError] = useState("");

  const currentConfig = resources[activeResource];
  const currentItems = data[activeResource] ?? [];
  const currentForm = forms[activeResource] ?? {};
  const editingId = editingIds[activeResource];

  const dashboardCards = useMemo(
    () => [
      { label: "Clientes", value: (data.clientes ?? []).length },
      { label: "Produtos", value: (data.produtos ?? []).length },
      { label: "Pedidos", value: (data.pedidos ?? []).length },
      { label: "Pagamentos", value: (data.pagamentos ?? []).length },
    ],
    [data],
  );

  useEffect(() => {
    let isMounted = true;

    async function loadAll() {
      try {
        setError("");
        const entries = await Promise.all(
          Object.entries(resources)
            .filter(([, config]) => config.endpoint)
            .map(async ([key, config]) => {
              const response = await api.get(config.endpoint);
              return [key, Array.isArray(response.data) ? response.data : []];
            }),
        );

        if (isMounted) {
          setData(Object.fromEntries(entries));
        }
      } catch {
        if (isMounted) {
          setError("Nao foi possivel carregar os dados. Verifique se o backend esta rodando.");
        }
      } finally {
        if (isMounted) {
          setLoading(false);
        }
      }
    }

    loadAll();

    return () => {
      isMounted = false;
    };
  }, []);

  function updateForm(field, value) {
    setForms((currentForms) => ({
      ...currentForms,
      [activeResource]: {
        ...currentForms[activeResource],
        [field]: value,
      },
    }));
  }

  function resetForm(resourceKey = activeResource) {
    setForms((currentForms) => ({
      ...currentForms,
      [resourceKey]: { ...resources[resourceKey].emptyForm },
    }));
    setEditingIds((currentIds) => ({ ...currentIds, [resourceKey]: null }));
  }

  function handleEdit(item) {
    const id = getId(item, currentConfig);

    if (id == null) {
      setError(`Nao foi possivel editar: ${currentConfig.label.toLowerCase()} sem ID.`);
      return;
    }

    const nextForm = currentConfig.toForm ? currentConfig.toForm(item) : pickFormValues(item, currentConfig);

    setForms((currentForms) => ({
      ...currentForms,
      [activeResource]: nextForm,
    }));
    setEditingIds((currentIds) => ({ ...currentIds, [activeResource]: String(id) }));
  }

  async function handleDelete(item) {
    const id = getId(item, currentConfig);
    const itemId = id == null ? "" : String(id);

    if (!itemId) {
      setError(`Nao foi possivel excluir: ${currentConfig.label.toLowerCase()} sem ID.`);
      return;
    }

    if (!window.confirm(`Excluir este registro de ${currentConfig.label.toLowerCase()}?`)) {
      return;
    }

    try {
      setError("");
      await api.delete(`${currentConfig.endpoint}/${encodeURIComponent(itemId)}`);
      await refreshResource(activeResource);

      if (editingId === itemId) {
        resetForm();
      }
    } catch {
      setError(`Nao foi possivel excluir ${currentConfig.label.toLowerCase()}.`);
    }
  }

  async function handleSubmit(event) {
    event.preventDefault();

    try {
      setSaving(true);
      setError("");

      const payload = currentConfig.toPayload ? currentConfig.toPayload(currentForm) : currentForm;

      if (editingId) {
        await api.put(`${currentConfig.endpoint}/${encodeURIComponent(editingId)}`, payload);
      } else {
        await api.post(currentConfig.endpoint, payload);
      }

      resetForm();
      await refreshResource(activeResource);
      if (activeResource === "pedidos") {
        await refreshResource("mesas");
      }
    } catch {
      setError(`Nao foi possivel salvar ${currentConfig.label.toLowerCase()}.`);
    } finally {
      setSaving(false);
    }
  }

  async function refreshResource(resourceKey) {
    const config = resources[resourceKey];
    const response = await api.get(config.endpoint);
    setData((currentData) => ({
      ...currentData,
      [resourceKey]: Array.isArray(response.data) ? response.data : [],
    }));
  }

  return (
    <div className="app">
      <aside className="sidebar">
        <h2>Restaurante</h2>

        <nav>
          {menuItems.map((item) => (
            <button
              type="button"
              key={item}
              className={activeResource === item ? "active" : ""}
              onClick={() => setActiveResource(item)}
            >
              {resources[item].label}
            </button>
          ))}
        </nav>
      </aside>

      <main className="content">
        <header className="header">
          <div>
            <h1>Sistema de Restaurante</h1>
          </div>

        </header>

        {error && <p className="alert">{error}</p>}

        {activeResource === "dashboard" && (
          <section className="dashboard-overview">
            <div className="dashboard-heading">
              <h2>Visao geral</h2>
              <p>Quantidade de registros cadastrados no sistema.</p>
            </div>

            <div className="cards">
              {dashboardCards.map((card) => (
                <div className="card" key={card.label}>
                  <span>{card.label}</span>
                  <strong>{card.value}</strong>
                </div>
              ))}
            </div>
          </section>
        )}

        {activeResource !== "dashboard" && (
          <>
            <section className="panel">
              <div className="panel-header">
                <h2>{currentConfig.label}</h2>
              </div>

              <div className="table-wrapper">
                <table>
                  <thead>
                    <tr>
                      {currentConfig.columns.map((column) => (
                        <th key={column.key}>{column.label}</th>
                      ))}
                      <th>Acoes</th>
                    </tr>
                  </thead>

                  <tbody>
                    {loading && (
                      <tr>
                        <td colSpan={currentConfig.columns.length + 1} className="empty-state">
                          Carregando...
                        </td>
                      </tr>
                    )}

                    {!loading && currentItems.length === 0 && (
                      <tr>
                        <td colSpan={currentConfig.columns.length + 1} className="empty-state">
                          Nenhum registro cadastrado.
                        </td>
                      </tr>
                    )}

                    {!loading &&
                      currentItems.map((item) => (
                        <tr key={getId(item, currentConfig)}>
                          {currentConfig.columns.map((column) => (
                            <td key={column.key}>{formatCell(item, column)}</td>
                          ))}
                          <td className="actions-cell">
                            <button type="button" className="edit" onClick={() => handleEdit(item)}>
                              Editar
                            </button>
                            <button type="button" className="delete" onClick={() => handleDelete(item)}>
                              Excluir
                            </button>
                          </td>
                        </tr>
                      ))}
                  </tbody>
                </table>
              </div>
            </section>

            <section className="panel">
              <div className="panel-header">
                <h2>{editingId ? `Editar ${currentConfig.label}` : `Novo ${currentConfig.label}`}</h2>
              </div>

              <form className="form" onSubmit={handleSubmit}>
                {currentConfig.fields.map((field) => (
                  <FormField
                    key={field.name}
                    field={field}
                    value={currentForm[field.name] ?? ""}
                    data={data}
                    onChange={(value) => updateForm(field.name, value)}
                  />
                ))}

                <div className="form-actions">
                  <button type="submit" disabled={saving}>
                    {saving ? "Salvando..." : "Salvar"}
                  </button>
                  {editingId && (
                    <button type="button" className="secondary" onClick={() => resetForm()}>
                      Cancelar
                    </button>
                  )}
                </div>
              </form>
            </section>
          </>
        )}
      </main>
    </div>
  );
}

function FormField({ field, value, data, onChange }) {
  if (field.type === "select") {
    if (field.options) {
      return (
        <select value={value} onChange={(event) => onChange(event.target.value)} required={field.required}>
          <option value="">{field.label}</option>
          {field.options.map((option) => (
            <option key={option} value={option}>
              {option}
            </option>
          ))}
        </select>
      );
    }

    const options = data[field.source] ?? [];
    const sourceConfig = resources[field.source];

    return (
      <select value={value} onChange={(event) => onChange(event.target.value)} required={field.required}>
        <option value="">{field.label}</option>
        {options.map((option) => (
          <option key={getId(option, sourceConfig)} value={getId(option, sourceConfig)}>
            {getOptionLabel(option, field.source)}
          </option>
        ))}
      </select>
    );
  }

  return (
    <input
      type={field.type ?? "text"}
      step={field.step}
      placeholder={field.label}
      value={value}
      onChange={(event) => onChange(event.target.value)}
      required={field.required}
    />
  );
}

function createInitialForms() {
  return Object.fromEntries(
    Object.entries(resources)
      .filter(([, config]) => config.emptyForm)
      .map(([key, config]) => [key, { ...config.emptyForm }]),
  );
}

function getId(item, config) {
  return item?.[config.idKey] ?? item?.id ?? item?._id ?? item?.codigo ?? item?.uuid;
}

function pickFormValues(item, config) {
  return Object.fromEntries(config.fields.map((field) => [field.name, item[field.name] ?? ""]));
}

function getNestedValue(item, path) {
  return path.split(".").reduce((value, key) => value?.[key], item);
}

function formatCell(item, column) {
  const value = getNestedValue(item, column.key);

  if (value == null || value === "") {
    return "-";
  }

  return column.format ? column.format(value) : String(value);
}

function getOptionLabel(item, resourceKey) {
  if (resourceKey === "mesas") {
    return `Mesa ${item.numero} - ${item.status}`;
  }

  if (resourceKey === "pedidos") {
    return `Pedido #${item.idPedido} - ${item.cliente?.nome ?? "sem cliente"}`;
  }

  return item.nome ?? `Registro #${getId(item, resources[resourceKey])}`;
}

function formatCurrency(value) {
  return Number(value).toLocaleString("pt-BR", {
    style: "currency",
    currency: "BRL",
  });
}

function formatDateTime(value) {
  return new Date(value).toLocaleString("pt-BR");
}

function toDateTimeInput(value) {
  return value ? String(value).slice(0, 16) : "";
}

export default App;
