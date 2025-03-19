import { Header, Banner, AboutWrapper, ClientsWrapper } from "./styles";

import { List, ArrowCircleLeft } from "phosphor-react";

import fluxyLogo from "../../assets/fluxy-logo.png";
import { useState } from "react";

import AnimatedBackgroundChart from "../../components/AnimatedBackgroundChart";

import { Reveal } from "../../components/Reveal";

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
                <h2>Conheça alguns dos nossos clientes</h2>
                <div className="clients">

                </div>
            </ClientsWrapper>
        </>
    );

}

