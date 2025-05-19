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
                </FormWrapper>
            </SignInUpWrapper>
        </>
    );
}