import styled from "styled-components";

export const InputWrapper = styled.div`
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-top: 1.5rem;
    margin-bottom: 1rem;

    & > button {
        padding: 0.75rem 1rem;
        border-radius: 8px;
        font-size: 1rem;
        justify-self: left;
        background-color: ${(props) => props.theme["purple-300"]};
        color: ${(props) => props.theme["white"]};;
        font-weight: bold;
        border: none;
        transition: all .2s;
    }
    
    & > button:hover {
        background-color: ${(props) => props.theme["purple-200"]};
        cursor: pointer;
    }

    @media (max-width: 880px) {
        flex-direction: column-reverse;

        button {
            width: 100%;
        }
    }
`;

export const DataTableWrapper = styled.div`
    max-height: 23rem; 
    overflow-y: auto; 
    width: 100%; 
    transition: all 3s;
    margin-bottom: 3rem;
    
    table {
        border-collapse: collapse;
        width: 100%;
        border-radius: 8px;
    }

    tr {
        background-color: white;
    }

    
    th, td {
        padding: 0.5rem;
        text-align: left;
    }
    
    td {
        border: 1px solid #ddd;
    }

    thead tr {
        background-color: ${(props) => props.theme["purple-300"]};
        color: ${(props) => props.theme["white"]};
    }

    tr:nth-child(even) {
        background-color: ${(props) => props.theme["white"]};
    }

    @media (max-width: 880px) {

        .responsive-table {
            border: 0;
        }

        .responsive-table caption {
            font-size: 1.3rem;
        }

        .responsive-table thead {
            border: none;
            clip: rect(0 0 0 0);
            height: 1px;
            margin: -1px;
            overflow: hidden;
            padding: 0;
            position: absolute;
            width: 1px;
        }

        .responsive-table tr {
            border-bottom: 3px solid ${(props) => props.theme["gray-200"]};
            display: block;
            margin-bottom: 0.625rem;
            padding: 0;
        }

        .responsive-table td {
            border-bottom: 1px solid ${(props) => props.theme["gray-200"]};
            display: block;
            font-size: 1rem;
            text-align: right;
        }

        .responsive-table td::before {
            content: attr(data-label);
            float: left;
            font-weight: 700;
            font-size: 0.9rem;
        }

        .responsive-table td:last-child {
            border-bottom: 0;
        }

        tr:nth-child(even) {
            background-color: white;
        }
    }
`;

