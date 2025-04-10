import styled from "styled-components";

export const ContactForm = styled.form`

        display: flex;
        flex-direction: column;
        gap: 1rem;
        margin: auto;
        margin-top: 2.5rem;
        min-width: 20rem;
        width: 50%;

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
    
    
            input {
                padding: 0.5rem;
                border-radius: 3px;
                border: none;
                background-color: ${(props) => props.theme["gray-200"]};
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

        @media (max-width: 880px) {

            width: 80%;

        }

`;
