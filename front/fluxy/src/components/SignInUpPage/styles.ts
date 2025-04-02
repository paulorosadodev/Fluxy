import styled from "styled-components";

export const SignInUpWrapper = styled.div`

    width: 100%;
    height: 100dvh;
    display: flex;
    justify-content: center;
    align-items: center;

    background-color: ${(props) => props.theme["purple-500"]};

    & > div:first-child {
        position: absolute;
        z-index: 0;
        width: 100dvw;
        overflow: hidden;
        opacity: 0.5;
    }

`;

export const FormWrapper = styled.div`

    background-color: ${(props) => props.theme["white"]};
    border-radius: 8px;
    padding: 3rem;
    
    display: flex;
    flex-direction: column;
    align-items: center;
    z-index: 1;

    overflow-y: auto;
    overflow-x: hidden;

    color: ${(props) => props.theme["purple-900"]};

    width: 60%;
    max-height: 100dvh;

    box-shadow: 0px 0px 20px rgba(0, 0, 0, 0.2);

    img {
        width: 15rem;
    }

    h1 {
        margin-top: 1rem;
        margin-bottom: 1rem;
    }

    form {

        width: 20rem;

        .input-wrapper {
            display: flex;
            flex-direction: column;
            gap: 0.4rem;
            text-align: left;
            width: 100%;
            margin-top:0.5rem;
    
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
            width: 100%;
            margin-top: 1rem;
            margin-bottom: 0.5rem;
        }

        button:disabled {
            opacity: 0.5;
        }

        button:not(:disabled):hover {
            cursor: pointer;
            background-color: ${(props) => props.theme["purple-300"]};
        }

        *:focus {
            outline: 0;
            box-shadow: 0 0 0 2px ${(props) => props.theme["purple-300"]};
        }

        .error {
            color: red;
            font-size: 0.8rem;
        }
    }

    @media (max-width: 880px) {

        flex: 1;
        height: 100dvh;

        form {
            width: 100%;
        }

    }

`;