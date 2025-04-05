import { Link, NavLink } from "react-router-dom";

import { SignOut, SquaresFour, Package, Truck, UsersThree, User, ShoppingCart } from "phosphor-react";

import { AsideMenuWrapper } from "./styles";

import fluxyLogo from "../../assets/fluxy-icon (3).png";
import { useAuth } from "../../hooks/useAuth";
import { useState } from "react";

export default function AsideMenu() {

    const { signOut } = useAuth();

    const [isNavHovered, setIsNavHovered] = useState(false);

    return (
        <AsideMenuWrapper className={isNavHovered ? "expanded" : ""}>
            <div className="logo-wrapper">
                <Link to="/">
                    <img className="fluxy-logo" src={fluxyLogo} />
                </Link>
            </div>
            <nav onMouseEnter={() => setIsNavHovered(true)} onMouseLeave={() => setIsNavHovered(false)}>
                <ul>
                    <li>
                        <NavLink to="/dashboard/inicio">
                            <SquaresFour size={32} />
                            <span className="menu-item">Início</span>
                        </NavLink>
                    </li>
                    <li>
                        <NavLink to="/dashboard/produtos">
                            <Package size={32} />
                            <span className="menu-item">Produtos</span>
                        </NavLink>
                    </li>
                    <li>
                        <NavLink to="/dashboard/fornecedores">
                            <Truck size={32} />
                            <span className="menu-item">Fornecedores</span>
                        </NavLink>
                    </li>
                    <li>
                        <NavLink to="/dashboard/funcionarios">
                            <UsersThree size={32} />
                            <span className="menu-item">Funcionários</span>
                        </NavLink>
                    </li>
                    <li>
                        <NavLink to="/dashboard/clientes">
                            <User size={32} />
                            <span className="menu-item">Clientes</span>
                        </NavLink>
                    </li>
                    <li>
                        <NavLink to="/dashboard/compras">
                            <ShoppingCart size={32} />
                            <span className="menu-item">Compras</span>
                        </NavLink>
                    </li>
                </ul>
            </nav>

            <div className="sign-out-wrapper">
                <SignOut onClick={signOut} className="sign-out" size={32} color="white" weight="bold" />
            </div>
        </AsideMenuWrapper>

    );
}
