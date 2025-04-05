import { ThemeProvider } from "styled-components";

import { Router } from "./Router";

import { GlobalStyle } from "./styles/global";
import { defaultTheme } from "./styles/themes/default";

import { AuthProvider } from "./context/AuthContext";
import { CookiesProvider } from "react-cookie";

export function App() {

    return (
        <>  
            <ThemeProvider theme={defaultTheme}>
                <CookiesProvider>
                    <AuthProvider>
                        <Router />
                        <GlobalStyle />
                    </AuthProvider>
                </CookiesProvider>
            </ThemeProvider>
        </>
    );

}
