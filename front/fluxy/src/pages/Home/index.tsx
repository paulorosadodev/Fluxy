import fluxyLogo from "../../assets/fluxy-logo.png";

import { Reveal } from "../../components/Reveal";
import { HomeNavBar } from "../../components/HomeNavBar";
import { ContactUsForm } from "../../components/ContactUsForm";
import AnimatedBackgroundChart from "../../components/AnimatedBackgroundChart";

import { Header, Banner, AboutWrapper, ClientsWrapper, ContactUsWrapper, FooterWrapper } from "./styles";

export function Home() {

    return (
        <>
            <Header>
                <a href="/">
                    <img src={fluxyLogo} alt="Logotipo da Fluxy" />
                </a>
                <HomeNavBar />
            </Header>
            <Banner>
                <h2>Visualize, gerencie, cresça!</h2>
                <AnimatedBackgroundChart />
            </Banner>
            <AboutWrapper id="about">
                <Reveal type="img" src="https://foodwatch.com.au/images/stories/p-healthy-eating/Aisles_Supermarket_Groceries.jpeg" alt="Imagem de um supermercado" delay={0.1} />
                <Reveal className="text-wrapper" type="div" delay={0.15}>
                    <h2>Olá, somos a Fluxy!</h2>
                    <p>Nossa plataforma foi criada para transformar a maneira como supermercados gerenciam seus dados. Oferecemos dashboards intuitivos e ferramentas poderosas para acompanhar vendas, estoque e desempenho em tempo real. Com insights inteligentes e uma interface fácil de usar, ajudamos gestores a tomarem decisões mais estratégicas e eficientes.</p>
                </Reveal>
            </AboutWrapper>
            <ClientsWrapper id="clients">
                <Reveal type="div" delay={0}> 
                    <h2 className="aa">Conheça alguns dos nossos clientes</h2>
                </Reveal>
                <div className="clients">
                    <Reveal title="Mix Mateus" type="div" delay={0.1}>
                        <img src="https://mateusmais.com.br/assets/logos/mix-mateus.svg" />
                    </Reveal>
                    <Reveal title="Assaí Atacadista" type="div" delay={0.2}>
                        <img src="https://upload.wikimedia.org/wikipedia/commons/thumb/5/5c/Assa%C3%AD_Atacadista_logo_2024.svg/2560px-Assa%C3%AD_Atacadista_logo_2024.svg.png" />
                    </Reveal>
                    <Reveal title="Carrefour" type="div" delay={0.3}>
                        <img src="https://companieslogo.com/img/orig/CA.PA-eef56ff6.png?t=1720244491" />
                    </Reveal>
                    <Reveal title="BIG Bompreço" type="div" delay={0.4}>
                        <img src="https://upload.wikimedia.org/wikipedia/commons/5/55/Logotipo_do_BIG_Bompre%C3%A7o.svg" />
                    </Reveal>
                    <Reveal title="Atacadão" type="div" delay={0.5}>
                        <img src="https://companieslogo.com/img/orig/CRFB3.SA-a1fe5fb4.png?t=1720244491" />
                    </Reveal>
                    <Reveal title="Deskontão" type="div" delay={0.6}>
                        <img src="https://deskontao.agilecdn.com.br/imgs/14618-logo-1701105120.png" />
                    </Reveal>
                </div>
            </ClientsWrapper>
            <ContactUsWrapper id="contact">
                <h2>Fale conosco</h2>
                <ContactUsForm />
            </ContactUsWrapper>
            <FooterWrapper>
                <p>© 2025 <a href="https://github.com/paulorosadodev/Fluxy"> Fluxy</a>. Todos os direitos reservados.</p> 
                <p>
                    Desenvolvido por <a target="__blank" href="https://www.linkedin.com/in/gustavo-mourato-1802b328a/">Gustavo Mourato</a>, <a target="__blank" href="https://www.linkedin.com/in/luankato/">Luan Kato</a>, <a target="__blank" href="https://www.linkedin.com/in/paulorosadodev/">Paulo Rosado</a> e <a target="__blank" href="https://www.linkedin.com/in/viniciusjord%C3%A3o/">Vinícius de Andrade</a>
                </p>
                <p>
                    Projeto para a disciplina de Modelagem e Projeto de Bancos de Dados na <a target="__blank" href="https://www.cesar.school/">CESAR School</a>
                </p>
            </FooterWrapper>
        </>
    );

}
