import { List, ArrowCircleLeft } from "phosphor-react";
import styled from "styled-components";

export const OpenMenuIcon = styled(List)`

    display: none;

    @media (max-width: 880px) {
        display: block;
        margin-top: 1rem;
        margin-left: 1rem;
        color: ${(props) => props.theme["purple-900"]};

        &.hide {
            display: none;
        }

        &:hover {
            cursor: pointer;
        }
    }

`;

export const CloseMenuIcon = styled(ArrowCircleLeft)`

    display: none;

    @media (max-width: 880px) {
        display: block;
        margin-top: 1rem;
        margin-left: 1rem;
        color: ${(props) => props.theme["white"]};
        z-index: 999;
    
        &.hide {
            display: none;
        }
    
        &:hover {
            cursor: pointer;
        }
    }


`;

export const AsideMenuWrapper = styled.aside`
    background-color: ${(props) => props.theme["purple-500"]};
    display: flex;
    flex-direction: column;
    padding: 1rem;
    width: 5rem;
    transition: width 0.3s ease;
    position: relative;
    overflow: hidden;

    overflow-y: auto;

    &.expanded {
        width: 12rem;
    }

    .logo-wrapper {
        display: flex;
        justify-content: center;
        width: 100%;
        margin-bottom: 1rem;
    }

    .sign-out-wrapper {
        display: flex;
        justify-content: center;
        width: 100%;
        margin-top: auto;
    }

    .fluxy-icon {
        width: 3.5rem;
        transition: transform 0.3s ease;
        margin: 0 auto;
    }

    .sign-out {
        cursor: pointer;
        margin-top: auto;
        margin-left: 0.5rem;
    }

    nav {
        margin-top: 1rem;
        width: 100%;
        height: 100%;
        margin-bottom: 20px;

        ul {
            display: flex;
            flex-direction: column;
            gap: 1rem;
            padding-left: 0;
            width: 100%;

            li {
                list-style: none;
                width: 100%;
            }

            a {
                display: flex;
                align-items: center;
                gap: 1rem;
                color: ${(props) => props.theme["gray-200"]};
                text-decoration: none;
                position: relative;
                transition: all 0.2s ease;

                svg {
                    flex-shrink: 0;
                    margin-left: 0.5rem;
                }

                .menu-item {
                    opacity: 0;
                    visibility: hidden;
                    white-space: nowrap;
                    transition: opacity 0.2s ease, visibility 0.2s ease;
                }
            }

            a:hover {
                color: ${(props) => props.theme["gray-50"]};
            }

            a.active {
                color: ${(props) => props.theme["white"]};
            }
        }
    }

    &.expanded .menu-item {
        opacity: 1;
        visibility: visible;
    }

    @media (max-width: 880px) {
        display: none;
        position: absolute;
        min-width: 100%;
        height: 100dvh;

        &#opened {
            display: flex;
        }

        li {
            display: flex;
            justify-content: center;
            
            a {
                font-size: 2rem;
                gap: 0.5rem !important;
    
                svg {
                    width: 4rem;
                    height: 3rem;
                }
            }
        }


        .menu-item {
            opacity: 1 !important;
            visibility: visible !important;
        }

        .fluxy-icon {
            width: 5rem;
        }

        .sign-out {
            width: 3rem;
            height: 3rem;
            margin-bottom: 1rem;
        }

    }
`;

