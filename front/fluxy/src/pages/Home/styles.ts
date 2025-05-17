import styled from "styled-components";

export const Header = styled.header`

    padding: 0rem 2rem 0rem 2rem;

    display: flex;
    justify-content: space-between;
    align-items: center;
    width: 100%;
    overflow-x: hidden;

    img {
        padding-left: 2rem;
        width: 20rem;
    }

    @media (max-width: 880px) {

        padding: 1rem 2rem 0rem 1rem;
        justify-content: space-between;

        img {
            padding-left: 0;
            width: 100%; 
            max-width: 12rem; 
            min-width: 6rem; 
            height: auto; 
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
    z-index: 0;
    position: relative;

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

    @media (max-width: 880px) {

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
        color: ${(props) => props.theme["purple-900"]};
    }

    p {
        font-size: 1.1rem;
        text-align: justify;
        line-height: 1.6rem;
    }

    @media (max-width: 880px) {
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
    padding: 20px 0px;

    text-align: center;

    h2 {
        font-size: 2.5rem;
        color: ${(props) => props.theme["purple-900"]};
    }

    .clients {
        background-color: ${(props) => props.theme["purple-500"]};
        margin: 2rem auto;
        padding: 2rem 0rem;
        width: 100%;

        display: flex;
        flex-wrap: wrap; 
        justify-content: center; 
        gap: 1rem; 

        div {
            box-sizing: content-box;
            width: calc((100% / 4) - 1rem);
            height: 5rem;
            object-fit: contain;
            background-color: ${(props) => props.theme["white"]};
            padding: 4rem 2rem;
            text-align: center;
            border-radius: 8px;

            display: flex;
            justify-content: center;
            align-items: center;

            img {
                width: 15rem;
                height: 5rem;
                object-fit: contain;
            }
        }
    }

    @media (max-width: 880px) {

        h2 {
            font-size: 2rem;
        }
        
        .clients {

            div {
                width: 5rem;
                height: 2rem;

                img {
                    width: 5rem;
                }
            }

        }

    }
`;

export const ContactUsWrapper = styled.div`

    text-align: center;
    margin-top: 3rem;
    margin-bottom: 5rem;
    overflow: hidden;

    padding-top: 1rem;

    h2 {
        color: ${(props) => props.theme["purple-900"]};
        font-size: 2.5rem;
    }

`;

export const FooterWrapper = styled.footer`
    
    background-color: ${(props) => props.theme["purple-500"]};

    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    gap: 1rem;

    padding: 2rem;

    p, a {
        color: ${(props) => props.theme["white"]};
        text-align: center;

        img {
            width: 1rem;
        }
    }

    a {
        text-decoration: none;
        font-weight: bold;
    }

    a:hover {
        text-decoration: underline;
        color: ${(props) => props.theme["gray-50"]};
    }

`;