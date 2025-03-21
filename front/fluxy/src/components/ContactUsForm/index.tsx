import { useForm } from "react-hook-form";

import { ContactForm } from "./styles";

import { z } from "zod";
import { zodResolver } from "@hookform/resolvers/zod";

import { Check, X } from "phosphor-react";
import { Reveal } from "../Reveal";
import { useState } from "react";

const contactUsSchema = z.object({
    name: z.string().refine((val) => val.length >= 3 && val.length <= 100, {
        message: "O nome deve ter entre 3 e 100 caracteres",
    }),

    email: z.string()
        .email({ message: "E-mail inválido" })
        .max(100, { message: "O e-mail deve ter no máximo 100 caracteres" }),

    message: z.string().refine((val) => val.length >= 10 && val.length <= 1000, {
        message: "A mensagem deve ter entre 10 e 1000 caracteres",
    })
});


type ContactUsSchema = z.infer<typeof contactUsSchema>

export function ContactUsForm() {

    const { register, handleSubmit, reset, watch, formState: {errors} } = useForm<ContactUsSchema>({
        resolver: zodResolver(contactUsSchema)
    });

    const [showPopUp, setShowPopUp] = useState(false);

    function handleContactUs() {
        reset();
        handleShowPopUp();
    }

    function handleShowPopUp() {
        setShowPopUp(prev => !prev);
    }

    const isSubmitDisabled = !(watch("name") && watch("email") && watch("message"));

    return (
        <ContactForm onSubmit={handleSubmit(handleContactUs)} noValidate >
            <div className="input-wrapper">
                <label htmlFor="name">Nome: {errors.name && <span className="error">{errors.name.message}</span>}</label>
                <input type="text" id="name" placeholder="Digite o seu nome" {...register("name")}/>
            </div>
            <div className="input-wrapper">
                <label htmlFor="email" >E-mail: {errors.email && <span className="error">{errors.email.message}</span>}</label>
                <input type="email" id="email" placeholder="Digite o seu e-mail" {...register("email")}/>
            </div>
            <div className="input-wrapper">
                <label htmlFor="message">Mensagem: {errors.message && <span className="error">{errors.message.message}</span>}</label>
                <textarea id="message" placeholder="Deixe uma mensagem" {...register("message")}></textarea>
            </div>
            <button type="submit" disabled={isSubmitDisabled}>Enviar</button>
            {showPopUp &&
                <Reveal type="div" className="popUp">
                    <Check size={30} color="green" weight="bold" />
                    <span>E-mail enviado com sucesso</span>
                    <X onClick={handleShowPopUp} size={20} color="gray" weight="bold" id="closePopUp"/>
                </Reveal>
            }
        </ContactForm>
    );

}