import { ReactNode } from "react";

import { SignInUpWrapper, FormWrapper } from "./styles";

import AnimatedBackgroundChart from "../AnimatedBackgroundChart";

import fluxyLogo from "../../assets/fluxy-logo.png";
import { Link } from "react-router-dom";

interface SignInUpPageProps {
    children: ReactNode;
    title: string
}

export function SignInUpPage({ children, title } : SignInUpPageProps) {

    const isSignUp = title === "Registre-se agora";

    return(
        <>
            <SignInUpWrapper>
                <AnimatedBackgroundChart />
                <FormWrapper>
                    <Link to={"/"}>
                        <img src={fluxyLogo} />
                    </Link>
                    <h1>{title}</h1>
                    {children}
                    {
                        isSignUp ? 
                            <p>
                                Já tem uma conta? <Link to={"/login"}>Entre aqui</Link>
                            </p> :
                            <p>
                                Não tem uma conta? <Link to={"/register"}>Registre-se aqui</Link>
                            </p>
                    }
                </FormWrapper>
            </SignInUpWrapper>
        </>
    );
}