import styled from "styled-components";

export const AsideMenuWrapper = styled.aside`
    background-color: ${(props) => props.theme["purple-500"]};
    display: flex;
    flex-direction: column;
    align-items: center;
    padding: 1rem;

    .fluxy-logo {
        width: 3.5rem;
    }

    .sign-out {
        cursor: pointer;
        margin-top: auto;
    }
`;