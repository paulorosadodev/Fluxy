import styled from "styled-components";

export const CardWrapper = styled.div`

    background-color: ${(props) => props.theme["white-dark"]};
    display: flex;
    align-items: center;
    justify-content: center;
    padding: 1rem 0rem;
    border-radius: 8px;
    color: ${(props) => props.theme["brown-800"]};
    border-left: 4px solid ${(props) => props.theme["purple-400"]};
    min-width: 20rem;

    #icon {
        color: ${(props) => props.theme["teal-500"]};
    }

    #data {
        font-weight: bold;
        margin-left: 0.5rem;
        margin-right: 0.3rem;
        font-size: 1.5rem;
    }

    #data.orange {
        color: ${(props) => props.theme["orange-300"]};
    }

    #data.red {
        color: ${(props) => props.theme["red-500"]};
    }

    #data.violet {
        color: ${(props) => props.theme["violet-700"]};
    }

    #data.green {
        color: ${(props) => props.theme["teal-500"]};
    }

`;