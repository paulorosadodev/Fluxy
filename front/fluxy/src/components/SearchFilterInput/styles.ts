import styled from "styled-components";

export const InputWrapper = styled.div`
    display: flex;
    justify-content: center;
    gap: 2rem;
    align-items: center;


    @media (max-width: 880px) {
        flex-direction: column-reverse;
        gap: 0.5rem;
        margin-bottom: 0.7rem;
        width: 100%;
    }
`;

export const SortInput = styled.div`
    display: flex;
    gap: 1rem;
    margin-left: auto;
`;

export const SearchInput = styled.input`
    background-color: ${props => props.theme["gray-200"]};
    color: ${props => props.theme["brown-800"]};
    padding: 0.75rem 1rem;
    border-radius: 8px;
    font-size: 1rem;
    width: 100%;
    max-width: 400px;
    border: none;
    outline: none;
    box-shadow: none;
    transition: all .2s;

    @media (max-width: 880px) {
        min-width: 100%;
    }

    &:focus {
        filter: brightness(1.06);
    }
`;

export const SortSelect = styled.select`
    background-color: ${props => props.theme["gray-200"]};
    color: ${props => props.theme["brown-800"]};
    padding: 0.75rem 1rem;
    border-radius: 8px;
    font-size: 1rem;
    border: none;
    outline: none;
    cursor: pointer;

    option {
        background-color: ${props => props.theme["gray-200"]};
        color: ${props => props.theme["brown-800"]};
    }

    &:hover {
        filter: brightness(1.06);
    }
`;

export const DirectionButton = styled.button`
    background-color: ${props => props.theme["gray-200"]};
    color: ${props => props.theme["brown-800"]};
    padding: 0.5rem 0.75rem;
    border: none;
    border-radius: 8px;
    cursor: pointer;
    display: flex;
    align-items: center;
    justify-content: center;
    transition: all .2s;

    &:hover {
        filter: brightness(1.06);
    }

`;
