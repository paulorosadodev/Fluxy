import { useState } from "react";
import { useForm } from "react-hook-form";

import { z } from "zod";
import { zodResolver } from "@hookform/resolvers/zod";

import { PopUp } from "../../components/PopUp";
import { SignInUpPage } from "../../components/SignInUpPage";

import { useAuth } from "../../hooks/useAuth";

import { Navigate } from "react-router-dom";

const loginSchema = z.object(
    {
        name: z.string(),
        password: z.string()
    });

type LoginSchema = z.infer<typeof loginSchema>

export function Login() {
    const [showPopUp, setShowPopUp] = useState(false);
    const [popUpMessage, setPopUpMessage] = useState("");
    const [popUpType, setPopUpType] = useState<"error" | "success">("success");
    const [isSubmitting, setIsSubmitting] = useState(false); 

    const { login, isAuthenticated } = useAuth();

    const { register, handleSubmit, reset, watch } = useForm<LoginSchema>({
        resolver: zodResolver(loginSchema)
    });

    const handleLogin = async (data: LoginSchema) => {
        setIsSubmitting(true); 
        const error = await login({
            name: data.name,
            password: data.password,
        });

        if (error) {
            setPopUpMessage(error);
            setPopUpType("error");
            setShowPopUp(true);
        } else {
            reset();
        }
        setIsSubmitting(false); 
    };

    if (isAuthenticated) {
        return <Navigate to={"/"} />;
    }

    const isSubmitDisabled = !(watch("name") && watch("password")) || isSubmitting;

    return (
        <>
            <SignInUpPage title="Entre em sua conta">
                <form onSubmit={handleSubmit(handleLogin)} noValidate>
                    <div className="input-wrapper">
                        <label htmlFor="name">Nome:</label>
                        <input id="name" type="text" placeholder="Digite seu nome" {...register("name")} />
                    </div>
                    <div className="input-wrapper">
                        <label htmlFor="password">Senha:</label>
                        <input id="password" type="password" placeholder="Digite sua senha" {...register("password")} />
                    </div>
                    <button type="submit" disabled={isSubmitDisabled}>
                        {isSubmitting ? "Entrando..." : "ENTRAR"}
                    </button>
                </form>
            </SignInUpPage>
            {showPopUp && 
                <PopUp type={popUpType} message={popUpMessage} show={showPopUp} onClose={() => setShowPopUp(false)} />
            }
        </>
    );
};