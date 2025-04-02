import { ThemeProvider } from "styled-components";

import { Router } from "./Router";

import { GlobalStyle } from "./styles/global";
import { defaultTheme } from "./styles/themes/default";

import { HelmetProvider, Helmet } from "react-helmet-async";

export function App() {

    return (
        <>  
            <HelmetProvider>
                <Helmet titleTemplate="%s | Fluxy" />
                <ThemeProvider theme={defaultTheme}>
                    <Router />
                    <GlobalStyle />
                </ThemeProvider>
            </HelmetProvider>
        </>
    );

}
