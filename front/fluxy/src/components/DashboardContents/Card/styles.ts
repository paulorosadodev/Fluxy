import styled, { keyframes } from "styled-components";

const fadeInUp = keyframes`
    from {
        opacity: 0;
        transform: translateY(20px);
    }
    to {
        opacity: 1;
        transform: translateY(0);
    }
`;

export const CardWrapper = styled.div<{ delay?: number }>`
    background-color: ${(props) => props.theme["white-dark"]};
    display: flex;
    align-items: center;
    justify-content: center;
    padding: 1rem 1rem;
    color: ${(props) => props.theme["brown-800"]};
    border-left: 4px solid ${(props) => props.theme["purple-400"]};
    min-width: 21rem;

    opacity: 0;
    animation: ${fadeInUp} 0.6s ease-out forwards;
    animation-delay: ${(props) => props.delay || 0}ms;

    #icon {
        color: ${(props) => props.theme["teal-500"]};
    }

    #data {
        font-weight: bold;
        margin-left: 0.5rem;
        margin-right: 0.4rem;
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