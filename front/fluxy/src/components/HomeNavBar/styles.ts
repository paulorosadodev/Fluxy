import { List } from "phosphor-react";
import styled from "styled-components";

export const NavBar = styled.nav`

    display: flex;
    align-items: center;

    ul {
        display: flex;
        align-items: center;
        gap: 1.5rem;
        padding: 6rem 3rem;
        flex-wrap: nowrap;
    }

    li, a {
        list-style: none;
        text-decoration: none;
        font-weight: 500;
        font-size: 1.3rem;
        color: ${(props) => props.theme["purple-900"]};

        &:hover {
            cursor: pointer;
        }
    }

    #button {
        background-color: ${(props) => props.theme["purple-500"]};
        padding: 1rem 2rem;
        border-radius: 8px;
        color: ${(props) => props.theme["white"]};
        font-weight: bold;
        font-size: 1rem;
        border: none;
        transition: background-color .1s;
        white-space: nowrap;
    }

    #button:hover {
        cursor: pointer;
        background-color: ${(props) => props.theme["purple-300"]};
    }

    @media (max-width: 880px) {

        &.deactive {
            display: none;
        }

        display: flex;
        flex-direction: column;
        justify-content: center;
        position: fixed;
        background-color: red;
        padding: 1rem;
        padding-bottom: 10px;
        top: 0;
        bottom: 0;
        right: 0;
        z-index: 4;
        width: 100dvw;
        height: 100dvh;
        overflow-y: auto;
        overflow-x: hidden;
        -webkit-overflow-scrolling: touch;   
        background-color: ${(props) => props.theme["purple-900"]};

        svg {
            position: fixed;
            top: 1.875rem;
            right: 42px;

            &:hover {
                cursor: pointer;
            }
        }

        ul {
            display: none; 
            align-items: flex-start;
        }

        ul.active {
            display: flex;
            flex-direction: column;
            gap: 1.5rem;
            padding: 6rem 3rem;
            opacity: 1;
            height: 100dvh;
            z-index: 3;
        }

        ul.deactive {
            display: none;
        }

        li, a {
            color: ${(props) => props.theme["white"]};
            font-size: 2.5rem;
        }

        #button {
            font-size: 2rem;
        }

        li:nth-last-child(2) {
            margin-bottom: 1rem;
        }

}

`;

export const MenuIcon = styled(List)`

    display: none;

    right: 40px;
    color: ${(props) => props.theme["purple-900"]};

    z-index: 4;

    &:hover {
        cursor: pointer;
    }

    @media (max-width: 880px) {

        display: block;
        width: 2.5rem;
        height: 2.5rem;

    }

`;
