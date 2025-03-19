import styled from "styled-components";

export const Header = styled.header`

    padding: 0rem 2rem 0rem 2rem;

    display: flex;
    justify-content: space-between;
    align-items: center;
    width: 100%;
    overflow-x: hidden;

    nav {
        display: flex;
        align-items: center;
    }

    img {
        padding-left: 2rem;
        width: 20rem;
    }

    ul {
        display: flex;
        align-items: center;
        gap: 1.5rem;
        padding: 6rem 3rem;
    }

    li, a {
        list-style: none;
        text-decoration: none;
        font-weight: 500;
        font-size: 1.3rem;
        color: ${(props) => props.theme["purple-900"]};

        &:hover {
            cursor: pointer;
        }
    }

    svg {
        display: none;

        right: 40px;
        color: ${(props) => props.theme["purple-900"]};

        z-index: 4;

        &:hover {
            cursor: pointer;
        }
    }

    #button {
        background-color: ${(props) => props.theme["purple-500"]};
        padding: 1rem 2rem;
        border-radius: 8px;
        color: ${(props) => props.theme["white"]};
        font-weight: bold;
        font-size: 1rem;
        border: none;
        transition: background-color .1s;
    }

    #button:hover {
        cursor: pointer;
        background-color: ${(props) => props.theme["purple-300"]};
    }

    @media (max-width: 800px) {

        padding: 1rem 2rem 0rem 1rem;
        justify-content: space-between;

        img {
            padding-left: 0;
            width: 100%; 
            max-width: 12rem; 
            min-width: 6rem; 
            height: auto; 
        }

        nav.deactive {
            display: none;
            background-color: red;
        }
        
        nav {
            display: flex;
            flex-direction: column;
            justify-content: center;
            position: fixed;
            background-color: red;
            padding: 1rem;
            padding-bottom: 10px;
            top: 0;
            bottom: 0;
            right: 0;
            z-index: 4;
            width: 100dvw;
            height: 100dvh;
            overflow-y: auto;
            overflow-x: hidden;
            -webkit-overflow-scrolling: touch;   
            background-color: ${(props) => props.theme["purple-900"]};

            svg {
                position: fixed;
                top: 1.875rem;
                right: 2.8125rem;
            }

        }

        ul {
            display: none; 
            align-items: flex-start;
        }

        ul.active {
            display: flex;
            flex-direction: column;
            gap: 1.5rem;
            padding: 6rem 3rem;
            opacity: 1;
            height: 100dvh;
            z-index: 3;
        }

        ul.deactive {
            display: none;
        }

        li, a {
            color: ${(props) => props.theme["white"]};
        }

        li:nth-last-child(2) {
            margin-bottom: 1rem;
        }

        svg {
            display: block;
            width: 2.5rem;
            height: 2.5rem;
        }

        #button {
            white-space: nowrap;
        }

    }

`;

export const Banner = styled.div`

    background-color: ${(props) => props.theme["purple-500"]};
    overflow: hidden;

    width: 100%;
    height: 20rem;
    display: flex;
    align-items: center;
    justify-content: center;

    h2 {
        position: absolute;
        z-index: 1;
        color: ${(props) => props.theme["white"]};
        text-align: center;
        font-size: 5rem;
        text-shadow: 2px 2px 1px ${(props) => props.theme["brown-800"]};
    }

    div {
        width: 100%;
        z-index: 0;
    }

    @media (max-width: 800px) {

        max-height: 15rem;
        margin-top: 1rem;

        h2 {
            font-size: 3rem;
            text-align: center;
        }

    }

`;

export const AboutWrapper = styled.div`

    display: flex;
    justify-content: space-between;
    gap: 2rem;

    padding: 5rem;

    img {
        width: 40%;
        border-radius: 8px;
    }
    
    .text-wrapper {
        display: flex;
        flex-direction: column;
        justify-content: center;
        gap: 2rem;
        color: ${(props) => props.theme["purple-900"]};
        flex: 1;
    }

    h2 {
        font-size: 2.5rem;
        text-align: center;
    }

    p {
        font-size: 1.1rem;
        text-align: justify;
        line-height: 1.6rem;
    }

    @media (max-width: 800px) {
        flex-direction: column-reverse;
        align-items: center;
        padding: 2rem 1rem;

        img {
            width: 90%;
        }

        p {
            font-size: 0.9rem;
        }
    }

`;

export const ClientsWrapper = styled.div`

    width: 100%;
    padding: 20px;

    text-align: center;

    h2 {
        font-size: 2.5rem;
        color: ${(props) => props.theme["purple-900"]};
    }

    .clients {
        background-color: ${(props) => props.theme["purple-500"]};
        width: 100%;
        height: 200px;
        margin-top: 2rem;
        border-radius: 8px;
    }
`;
