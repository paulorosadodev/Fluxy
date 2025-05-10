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

    .unauthorized-container {
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: center;
        height: 100vh;
        color: #4b5563; 
        text-align: center;
        padding: 16px;
    }

    .unauthorized-icon {
        color: ${props => props.theme["orange-500"]};
        margin-bottom: 16px;
    }

    .unauthorized-title {
        font-size: 24px;
        font-weight: 600;
        margin: 0;
    }

    .unauthorized-text {
        font-size: 14px;
        margin-top: 8px;
    }

`;
