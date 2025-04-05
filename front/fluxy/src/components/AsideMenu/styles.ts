import styled from "styled-components";

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

    .fluxy-logo {
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
`;

