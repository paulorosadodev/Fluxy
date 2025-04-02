import { Helmet } from "react-helmet-async";

import { SignInUpPage } from "../../components/SignInUpPage";

export function Login() {
    return (
        <>
            <Helmet title="Login" />

            <SignInUpPage title="Entre em sua conta">
                <form>
                    <div className="input-wrapper">
                        <label htmlFor="name">Nome:</label>
                        <input id="name" type="text" placeholder="Digite seu nome" />
                    </div>
                    <div className="input-wrapper">
                        <label htmlFor="password">Senha:</label>
                        <input id="password" type="text" placeholder="Digite sua senha" />
                    </div>
                    <button type="submit">ENTRAR</button>
                </form>
            </SignInUpPage>
        </>
    );
}
