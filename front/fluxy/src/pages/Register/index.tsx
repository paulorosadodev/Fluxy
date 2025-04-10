import { useForm } from "react-hook-form";

import { z } from "zod";
import { zodResolver } from "@hookform/resolvers/zod";

import { PopUp } from "../../components/PopUp";
import { SignInUpPage } from "../../components/SignInUpPage";

import * as auth from "../../services/endpoints/auth";

import { useState } from "react";
import { Navigate } from "react-router-dom";

const registerSchema = z.object(
    {
        name: z.string()
            .min(3, { message: "O nome deve ter no mínimo 3 caracteres" })
            .max(100, { message: "O nome deve ter no máximo 100 caracteres" }),

        password: z.string()
            .min(6, { message: "A senha deve ter no mínimo 6 caracteres" })
            .regex(/[A-Za-z]/, { message: "A senha deve conter ao menos uma letra" })
            .regex(/[0-9]/, { message: "A senha deve conter ao menos um número" })
            .max(100, { message: "A senha deve ter no máximo 100 caracteres" }),

        confirmPassword: z.string()
    })
    .refine((data) => data.password === data.confirmPassword, {
        message: "As senhas não coincidem",
        path: ["confirmPassword"], 
    });

type RegisterSchema = z.infer<typeof registerSchema>

export function Register() {
    const { register, handleSubmit, reset, watch, formState: {errors} } = useForm<RegisterSchema>({
        resolver: zodResolver(registerSchema)
    });

    const [showPopUp, setShowPopUp] = useState(false);
    const [popUpMessage, setPopUpMessage] = useState("");
    const [popUpType, setPopUpType] = useState<"error" | "success">("success");
    const [isSubmitting, setIsSubmitting] = useState(false); 
    const [redirectToLogin, setRedirectToLogin] = useState(false);

    const handleRegister = async (data: RegisterSchema) => {
        setIsSubmitting(true); 

        try {
            await auth.register({
                name: data.name,
                password: data.password,
                confirmPassword: data.confirmPassword,
            });

            reset();
            setPopUpMessage("Loja cadastrada com sucesso");
            setPopUpType("success");
            setShowPopUp(true);
            setTimeout(() => {
                setRedirectToLogin(true); 
            }, 2000);

        } catch (error: any) {
            setPopUpMessage(error.message);
            setPopUpType("error");
            setShowPopUp(true);
        } finally {
            setIsSubmitting(false); 
        }
    };
    

    if (redirectToLogin) {
        return <Navigate to="/login" />;
    }

    const isSubmitDisabled = !(watch("name") && watch("password") && watch("confirmPassword")) || isSubmitting;

    return (
        <>  
            <SignInUpPage title="Registre-se">
                <form onSubmit={handleSubmit(handleRegister)} noValidate >
                    <div className="input-wrapper">
                        <label htmlFor="name">
                            Nome: {errors.name && <span className="error">{errors.name.message}</span>}
                        </label>
                        <input id="name" type="text" placeholder="Digite seu nome" {...register("name")} />
                    </div>
                    <div className="input-wrapper">
                        <label htmlFor="password">
                            Senha: {errors.password && <span className="error">{errors.password.message}</span>}
                        </label>
                        <input id="password" type="password" placeholder="Digite sua senha" {...register("password")} />
                    </div>
                    <div className="input-wrapper">
                        <label htmlFor="confirm-password">
                            Confirme sua senha: {errors.confirmPassword && <span className="error">{errors.confirmPassword.message}</span>}
                        </label>
                        <input id="confirm-password" type="password" placeholder="Digite sua senha novamente" {...register("confirmPassword")} />
                    </div>
                    <button type="submit" disabled={isSubmitDisabled}>
                        {isSubmitting ? "Registrando..." : "REGISTRAR"}
                    </button>
                </form>
            </SignInUpPage>
            {showPopUp && 
                <PopUp type={popUpType} message={popUpMessage} show={showPopUp} onClose={() => setShowPopUp(false)} />
            }
        </>
    );
}
