import { ThemeProvider } from "styled-components";

import { Router } from "./Router";

import { GlobalStyle } from "./styles/global";
import { defaultTheme } from "./styles/themes/default";

import { CookiesProvider } from "react-cookie";
import { AuthProvider } from "./context/AuthContext";
import { DataProvider } from "./context/DataContext";

export function App() {

    return (
        <>  
            <ThemeProvider theme={defaultTheme}>
                <CookiesProvider>
                    <AuthProvider>
                        <DataProvider>
                            <Router />
                            <GlobalStyle />
                        </DataProvider>
                    </AuthProvider>
                </CookiesProvider>
            </ThemeProvider>
        </>
    );

}
