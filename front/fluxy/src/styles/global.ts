import { createGlobalStyle } from "styled-components";

export const GlobalStyle = createGlobalStyle`

    * {
        margin: 0;
        padding: 0;
        box-sizing: border-box;
    }

    html, body {
        background-color: ${props => props.theme["white"]};
        color: ${props => props.theme["brown-800"]};
        -webkit-font-smoothing: antialiased;
        height: 100dvh;
        scroll-behavior: smooth;
    }

    body, input, textarea, button {
        font-family: 'Inter', sans-serif;
        font-weight: 400;
    }

`;
