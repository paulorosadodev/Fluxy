import styled from "styled-components";
import { Outlet } from "react-router-dom";
import AsideMenu from "../components/AsideMenu/";

const LayoutContainer = styled.div`
    display: flex;
    height: 100vh;

    @media (max-width: 880px) {
        flex-direction: column;
    }
`;

const Main = styled.main`
    flex: 1;
    overflow-y: auto;
    
    #main {
        padding: 3rem 3rem;
        margin-left: 5rem;
        
        .dashboard {
            margin-top: 1rem;
            margin-bottom: 2rem;
        }

        @media (max-width: 880px) {
            margin-left: 0;
        }

    }
    
`;

export function DefaultLayout() {
    return (
        <LayoutContainer>
            <AsideMenu />
            <Main>
                <Outlet />
            </Main>
        </LayoutContainer>
    );
}
