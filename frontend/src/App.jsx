
import './App.css'

import "./App.css";

function App() {
  return (
    <div className="app">
      <aside className="sidebar">
        <h2>Restaurante</h2>

        <nav>
          <a className="active">Dashboard</a>
          <a>Clientes</a>
          <a>Produtos</a>
          <a>Pedidos</a>
          <a>Pagamentos</a>
        </nav>
      </aside>

      <main className="content">
        <header className="header">
          <div>
            <h1>Sistema de Restaurante</h1>
            <p>Controle de clientes, produtos e pedidos</p>
          </div>

          <button>Novo Pedido</button>
        </header>

        <section className="cards">
          <div className="card">
            <span>Clientes</span>
            <strong>24</strong>
          </div>

          <div className="card">
            <span>Pedidos hoje</span>
            <strong>12</strong>
          </div>

          <div className="card">
            <span>Produtos</span>
            <strong>38</strong>
          </div>

          <div className="card">
            <span>Faturamento</span>
            <strong>R$ 842,50</strong>
          </div>
        </section>

        <section className="panel">
          <div className="panel-header">
            <h2>Clientes cadastrados</h2>
            <button>Cadastrar Cliente</button>
          </div>

          <table>
            <thead>
              <tr>
                <th>Nome</th>
                <th>Telefone</th>
                <th>Email</th>
                <th>Endereço</th>
                <th>Ações</th>
              </tr>
            </thead>

            <tbody>
              <tr>
                <td>João Silva</td>
                <td>48999990000</td>
                <td>joao@email.com</td>
                <td>Rua A, 123</td>
                <td>
                  <button className="edit">Editar</button>
                  <button className="delete">Excluir</button>
                </td>
              </tr>

              <tr>
                <td>Maria Souza</td>
                <td>48988881111</td>
                <td>maria@email.com</td>
                <td>Rua B, 456</td>
                <td>
                  <button className="edit">Editar</button>
                  <button className="delete">Excluir</button>
                </td>
              </tr>
            </tbody>
          </table>
        </section>

        <section className="panel">
          <div className="panel-header">
            <h2>Novo cliente</h2>
          </div>

          <form className="form">
            <input type="text" placeholder="Nome do cliente" />
            <input type="text" placeholder="Telefone" />
            <input type="email" placeholder="E-mail" />
            <input type="text" placeholder="Endereço" />
            <button type="button">Salvar Cliente</button>
          </form>
        </section>
      </main>
    </div>
  );
}

export default App;