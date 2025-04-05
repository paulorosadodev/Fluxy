import styled from "styled-components";
import { Outlet } from "react-router-dom";
import AsideMenu from "../components/AsideMenu/";

const LayoutContainer = styled.div`
    display: flex;
    height: 100vh;
`;

const Main = styled.main`
    flex: 1;
    padding: 1rem;
    overflow-y: auto;
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
