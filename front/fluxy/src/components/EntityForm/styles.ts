import styled from "styled-components";

export const EntityFormWrapper = styled.div`

    display: none;

    &.active {
        position: absolute;
        flex: 1;
        z-index: 998;
        height: 100dvh;
        width: 100%;
    
        display: flex;
        justify-content: center;
        align-items: center;

        overflow-y: hidden;
    
        backdrop-filter: blur(2px);
        background-color: rgba(0, 0, 0, 0.2);

        @media (max-width: 880px) {
            align-items: flex-start;
            padding: 1rem 1rem;
            background-color: transparent;

        }
    }


`;

export const FormWrapper = styled.div`
    position: relative;
    display: flex;
    flex-direction: column;
    justify-content: center;
    text-align: center;
    padding: 5rem;
    z-index: 999;

    width: fit-content;

    background-color: white;
    border-radius: 8px;

    svg {
        position: absolute;
        right: 30px;
        top: 30px;
        cursor: pointer;
    }

    form {
        display: flex;
        flex-direction: column;

        .fields-wrapper {
            display: flex;
            gap: 1rem;
        }

        display: flex;
        flex-direction: column;
        gap: 1rem;
        margin: auto;
        margin-top: 2.5rem;

        strong {
            color: ${(props) => props.theme["purple-300"]};
        }

        *:focus {
            outline: 0;
            box-shadow: 0 0 0 2px ${(props) => props.theme["purple-300"]};
        }

        .error {
            color: red;
            font-size: 0.8rem;
        }

        .input-wrapper {
            display: flex;
            flex-direction: column;
            gap: 0.4rem;
            text-align: left;
            width: 100%;
    
            input, select {
                padding: 0.5rem;
                border-radius: 3px;
                border: none;
                background-color: ${(props) => props.theme["gray-200"]};
                font-size: 1rem;
                appearance: none; 
            }

            select {
                padding-right: 2rem; 
                background-image: url('data:image/svg+xml,%3Csvg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="none" stroke="currentColor" viewBox="0 0 16 16"%3E%3Cpath d="M1 4l7 7 7-7" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"%3E%3C/path%3E%3C/svg%3E');
                background-position: right 0.75rem center;
                background-repeat: no-repeat;
                background-size: 12px 8px;
            }
    
            textarea {
                resize: none;
                padding: 0.3rem;
                border-radius: 3px;
                border: none;
                background-color: ${(props) => props.theme["gray-200"]};
                margin-bottom: 1rem;
                height: 6rem;
            }
        }

        button {
            background-color: ${(props) => props.theme["purple-500"]};
            padding: 1rem 2rem;
            border-radius: 8px;
            color: ${(props) => props.theme["white"]};
            font-weight: bold;
            font-size: 1rem;
            border: none;
            transition: background-color .1s;
        }

        button:disabled {
            opacity: 0.5;
        }

        button:not(:disabled):hover {
            cursor: pointer;
            background-color: ${(props) => props.theme["purple-300"]};
        }
    }
    
    @media (max-width: 880px) {
        padding: 2rem;
        box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.2);
    
        form {
            margin-top: 1rem;
            padding: 0;
            width: 100%;
        }

        .fields-wrapper {
            flex-direction: column;
            gap: 1rem;
        }

        .input-wrapper {
            width: 100%;
        }

        button {
            width: 100%;
        }
    }
`;
