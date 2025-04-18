import styled from "styled-components";

export const InputWrapper = styled.div`
    display: flex;
    width: 100%;
    justify-content: center;
    gap: 2rem;
    align-items: center;
    margin-top: 1.5rem;
    margin-bottom: 1rem;
`;

export const SortInput = styled.div`
    display: flex;
    gap: 1rem;
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

`;
