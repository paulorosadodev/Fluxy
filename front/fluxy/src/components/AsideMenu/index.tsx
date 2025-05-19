import { Link, NavLink } from "react-router-dom";

import { SignOut, SquaresFour, Package, Truck, UsersThree, User, ShoppingCart } from "phosphor-react";

import { AsideMenuWrapper, OpenMenuIcon, CloseMenuIcon } from "./styles";

import fluxyIcon from "../../assets/fluxy-icon.png";

import { useAuth } from "../../hooks/useAuth";
import { useState } from "react";

export default function AsideMenu() {

    const { signOut, role } = useAuth();

    const [isNavHovered, setIsNavHovered] = useState(false);
    const [isMenuOpened, setIsMenuOpened] = useState(false);

    const openMenu = () => {
        setIsMenuOpened(true);
    };

    const closeMenu = () => {
        setIsMenuOpened(false);
    };

    return (
        <>
            <OpenMenuIcon onClick={openMenu} className={"open-menu " + (isMenuOpened ? "hide" : "")} size={50} weight="bold" />
            <CloseMenuIcon onClick={closeMenu} className={"close-menu " + (!isMenuOpened ? "hide" : "")} size={50} weight="bold" />
            <AsideMenuWrapper id={isMenuOpened ? "opened" : ""} className={isNavHovered ? "expanded" : ""}>
                <div className="logo-wrapper">
                    <Link to="/">
                        <img className="fluxy-icon" src={fluxyIcon} />
                    </Link>
                </div>
                <nav onMouseEnter={() => setIsNavHovered(true)} onMouseLeave={() => setIsNavHovered(false)}>
                    <ul>
                        <li>
                            <NavLink to="/dashboard/inicio" onClick={closeMenu}>
                                <SquaresFour size={32} />
                                <span className="menu-item">Início</span>
                            </NavLink>
                        </li>
                        {role.includes("products") && 
                            <li>
                                <NavLink to="/dashboard/produtos" onClick={closeMenu}>
                                    <Package size={32} />
                                    <span className="menu-item">Produtos</span>
                                </NavLink>
                            </li>
                        }
                        {role.includes("suppliers") && 
                            <li>
                                <NavLink to="/dashboard/fornecedores" onClick={closeMenu}>
                                    <Truck size={32} />
                                    <span className="menu-item">Fornecedores</span>
                                </NavLink>
                            </li>
                        }
                        {role.includes("employees") && 
                            <li>
                                <NavLink to="/dashboard/funcionarios" onClick={closeMenu}>
                                    <UsersThree size={32} />
                                    <span className="menu-item">Funcionários</span>
                                </NavLink>
                            </li>
                        }
                        {role.includes("customers") && 
                            <li>
                                <NavLink to="/dashboard/clientes" onClick={closeMenu}>
                                    <User size={32} />
                                    <span className="menu-item">Clientes</span>
                                </NavLink>
                            </li>
                        }
                        {role.includes("purchases") && 
                            <li>
                                <NavLink to="/dashboard/compras" onClick={closeMenu}>
                                    <ShoppingCart size={32} />
                                    <span className="menu-item">Compras</span>
                                </NavLink>
                            </li>
                        }
                    </ul>
                </nav>

                <div className="sign-out-wrapper">
                    <SignOut onClick={signOut} className="sign-out" size={32} color="white" weight="bold" />
                </div>
            </AsideMenuWrapper>
        </>
    );
}
