import { useState } from "react";

import { List, ArrowCircleLeft } from "phosphor-react";

import fluxyLogo from "../../assets/fluxy-logo.png";

import { Reveal } from "../../components/Reveal";
import AnimatedBackgroundChart from "../../components/AnimatedBackgroundChart";

import { Header, Banner, AboutWrapper, ClientsWrapper } from "./styles";

export function Home() {

    const [showMenu, setShowMenu] = useState(false);

    const handleShowMenu = () => {
        setShowMenu(() => {
            document.body.style.overflow = "hidden";
            return true;
        });
    };

    const handleCloseMenu = () => {
        setShowMenu(() => {
            document.body.style.overflow = "auto";
            return false;
        });
    };

    return (
        <>
            <Header>
                <img src={fluxyLogo} alt="Logotipo da Fluxy" />
                <List size={50} weight="bold" onClick={handleShowMenu} />
                <nav className={showMenu ? "active" : "deactive"}>
                    {showMenu &&
                        <ArrowCircleLeft size={50} color="white" weight="bold" onClick={handleCloseMenu} />
                    }
                    <ul className="active">
                        <li><a href="#about" onClick={handleCloseMenu}>Sobre</a></li>
                        <li><a href="#clients" onClick={handleCloseMenu}>Clientes</a></li>
                        <li><a href="#" onClick={handleCloseMenu}>Contato</a></li>
                        <li><a id="button" href="#" onClick={handleCloseMenu}>Comece agora</a></li>
                    </ul>
                </nav>
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
                    <h2>Conheça alguns dos nossos clientes</h2>
                </Reveal>
                <div className="clients">
                    <Reveal title="Mix Mateus" type="div" delay={0.1}>
                        <img src="https://mateusmais.com.br/assets/logos/mix-mateus.svg" />
                    </Reveal>
                    <Reveal title="Assaí Atacadista" type="div" delay={0.2}>
                        <img src="https://upload.wikimedia.org/wikipedia/commons/thumb/5/5c/Assa%C3%AD_Atacadista_logo_2024.svg/2560px-Assa%C3%AD_Atacadista_logo_2024.svg.png" />
                    </Reveal>
                    <Reveal title="Carrefour" type="div" delay={0.3}>
                        <img src="https://upload.wikimedia.org/wikipedia/commons/thumb/1/12/Carrefour_logo_no_tag.svg/2560px-Carrefour_logo_no_tag.svg.png" />
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
        </>
    );

}

