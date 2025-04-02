import { Helmet } from "react-helmet-async";

import { SignInUpPage } from "../../components/SignInUpPage";

export function Register() {
    return (
        <>  
            <Helmet title="Registro" />

            <SignInUpPage title="Registre-se agora">
                <form>
                    <div className="input-wrapper">
                        <label htmlFor="name">Nome:</label>
                        <input id="name" type="text" placeholder="Digite seu nome" />
                    </div>
                    <div className="input-wrapper">
                        <label htmlFor="password">Senha:</label>
                        <input id="password" type="text" placeholder="Digite sua senha" />
                    </div>
                    <div className="input-wrapper">
                        <label htmlFor="confirm-password">Confirme sua senha:</label>
                        <input id="confirm-password" type="text" placeholder="Digite sua senha novamente" />
                    </div>
                    <button type="submit">REGISTRAR</button>
                </form>
            </SignInUpPage>
        </>
    );
}
