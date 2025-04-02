import { useForm } from "react-hook-form";

import { z } from "zod";
import { zodResolver } from "@hookform/resolvers/zod";

import { SignInUpPage } from "../../components/SignInUpPage";

const loginSchema = z.object(
    {
        name: z.string(),
        password: z.string()
    });

type LoginSchema = z.infer<typeof loginSchema>

export function Login() {

    const { register, handleSubmit, reset, watch } = useForm<LoginSchema>({
        resolver: zodResolver(loginSchema)
    });

    function handleLogin() {
        reset();
    }

    const isSubmitDisabled = !(watch("name") && watch("password"));

    return (
        <>
            <SignInUpPage title="Entre em sua conta">
                <form onSubmit={handleSubmit(handleLogin)} noValidate >
                    <div className="input-wrapper">
                        <label htmlFor="name">Nome:</label>
                        <input id="name" type="text" placeholder="Digite seu nome" {...register("name")} />
                    </div>
                    <div className="input-wrapper">
                        <label htmlFor="password">Senha:</label>
                        <input id="password" type="password" placeholder="Digite sua senha" {...register("password")} />
                    </div>
                    <button type="submit" disabled={isSubmitDisabled}>ENTRAR</button>
                </form>
            </SignInUpPage>
        </>
    );
}
