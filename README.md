![banner](https://github.com/user-attachments/assets/da9b063e-ac48-4dd0-81e6-aee9dae42f44)

O **Fluxy** é uma plataforma completa de gestão para supermercados, desenvolvida com tecnologias modernas para transformar a maneira como estabelecimentos comerciais gerenciam seus dados. A solução oferece dashboards intuitivos e ferramentas poderosas para acompanhar vendas, estoque, fornecedores, funcionários e clientes em tempo real.

---

### ✨ Características Principais

- **📊 Dashboards Interativos**: Visualizações em tempo real com gráficos e métricas detalhadas
- **🛒 Gestão de Produtos**: Controle completo do estoque, categorias e preços
- **👥 Gestão de Clientes**: Cadastro de pessoas físicas e jurídicas
- **🚚 Gestão de Fornecedores**: Controle de fornecedores e entregas de produtos
- **👨‍💼 Gestão de Funcionários**: Administração de equipes e cargos
- **💰 Controle de Vendas**: Acompanhamento detalhado de compras e métodos de pagamento
- **🔐 Autenticação Segura**: Sistema de login com JWT e controle de permissões por cargo
- **📱 Interface Responsiva**: Experiência otimizada para desktop e mobile

## 🏗️ Arquitetura do Sistema

### Backend (Spring Boot)
- **Framework**: Spring Boot 3.4.4
- **Banco de Dados**: MySQL com JDBC
- **Segurança**: Spring Security + JWT

### Frontend (React)
- **Framework**: React 19 com TypeScript
- **Build Tool**: Vite
- **Estilização**: Styled Components
- **Roteamento**: React Router DOM
- **Validação**: Zod
- **Gráficos**: Recharts
- **Ícones**: Phosphor Icons
- **Requisições**: Axios

## 🗄️ Modelo de Dados

O sistema possui as seguintes entidades principais:

### Entidades Core
- **Pessoa**: Dados básicos (endereço, telefones)
- **Cliente**: Pessoa física (CPF) ou jurídica (CNPJ)
- **Funcionário**: Dados trabalhistas (salário, cargo, setor)
- **Fornecedor**: Empresas parceiras

### Produtos e Vendas
- **Categoria**: Classificação de produtos
- **Produto**: Itens do estoque (código EA, preço, quantidade)
- **Compra**: Transações de venda
- **Entrega**: Abastecimento de estoque pelos fornecedores

### Recursos Avançados
- **Histórico de Preços**: Rastreamento automático via triggers
- **Métodos de Pagamento**: Diferentes formas de pagamento
- **Usuários**: Sistema de autenticação e autorização

## ⚙️ Configuração e Instalação

### Pré-requisitos
- **Java 17+**
- **Node.js 18+**
- **MySQL 8.0+**
- **Maven 3.6+**

### 1. Configuração do Banco de Dados

```bash
# Criar database MySQL
CREATE DATABASE fluxy_db;

# Executar scripts de criação
mysql -u root -p fluxy_db < scripts/create.sql
mysql -u root -p fluxy_db < scripts/populate.sql
```

### 2. Configuração do Backend

```bash
# Navegar para o diretório backend
cd back/fluxy

# Configurar application.properties
cp src/main/resources/application.properties.example src/main/resources/application.properties

# Editar configurações do banco
spring.datasource.url=jdbc:mysql://localhost:3306/fluxy_db
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha

# Compilar e executar
./mvnw clean install
./mvnw spring-boot:run
```

### 3. Configuração do Frontend

```bash
# Navegar para o diretório frontend
cd front/fluxy

# Instalar dependências
npm install

# Configurar variáveis de ambiente
echo "VITE_API_URL=http://localhost:8080" > .env

# Executar em modo desenvolvimento
npm run dev
```

## 🔐 Autenticação e Autorização

### Usuário Padrão
- **Usuário**: `root`
- **Senha**: `root123`
- **Permissões**: Administrador (acesso total)

### Sistema de Permissões
O sistema possui controle granular de acesso baseado em cargos:

- **Admin**: Acesso total ao sistema
- **Manager**: Acesso a dashboards e relatórios
- **Vendedor**: Acesso a vendas e clientes
- **Estoquista**: Acesso a produtos e fornecedores

## 📊 Dashboards e Funcionalidades

### Dashboard Principal
- Visão geral de todas as métricas
- Gráficos de receita, despesas e lucro
- Top rankings de produtos, clientes e funcionários

### Gestão de Produtos
- CRUD completo de produtos e categorias
- Controle de estoque com alertas
- Histórico de alterações de preços
- Análise de produtos mais/menos vendidos

### Gestão de Clientes
- Cadastro de pessoas físicas e jurídicas
- Análise de clientes por cidade
- Ranking de clientes mais compradores

### Gestão de Fornecedores
- Cadastro de fornecedores e entregas
- Controle de custos de abastecimento
- Análise de fornecedores por desempenho

### Gestão de Funcionários
- Cadastro com dados trabalhistas
- Análise por cargo e turno de trabalho
- Controle de salários e setores

### Gestão de Vendas
- Registro de compras com múltiplos produtos
- Diferentes métodos de pagamento
- Análise temporal de vendas

## 🤝 Contribuição

### Desenvolvedores
- **[Gustavo Mourato](https://www.linkedin.com/in/gustavo-mourato-1802b328a/)** - Back End Developer
- **[Luan Kato](https://www.linkedin.com/in/luankato/)** - Back End Developer  
- **[Paulo Rosado](https://www.linkedin.com/in/paulorosadodev/)** - Full Stack Developer
- **[Vinícius de Andrade](https://www.linkedin.com/in/viniciusjord%C3%A3o/)** - Back End Developer

## 📄 Licença

Este projeto está licenciado sob a Licença MIT - veja o arquivo [LICENSE](LICENSE) para mais detalhes.

## 🎓 Contexto Acadêmico

Este projeto foi desenvolvido como trabalho da disciplina de **Modelagem e Projeto de Bancos de Dados** no 4º período do curso de Ciência da Computação na [CESAR School](https://www.cesar.school/).

### Objetivos Acadêmicos
- Aplicar conceitos de modelagem de banco de dados
- Implementar sistema completo com frontend e backend
- Utilizar boas práticas de desenvolvimento
- Desenvolver interface de usuário moderna e responsiva

---

<div align="center">
  <p><strong>Fluxy © 2025 - Visualize, gerencie, cresça!</strong></p>
  <p>Desenvolvido com 🧡 pelos estudantes da CESAR School</p>
</div>
