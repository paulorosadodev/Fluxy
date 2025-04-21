import { useForm, SubmitHandler, useFieldArray } from "react-hook-form";
import { z } from "zod";
import { zodResolver } from "@hookform/resolvers/zod";
import { FormControllers } from "../DataTable";
import { EntityFormWrapper, FormWrapper } from "./styles";
import { X, Plus, Trash } from "phosphor-react";
import { Dispatch, SetStateAction, useEffect, useState } from "react";
import { PopUp } from "../PopUp";
import { formatCEP, formatCNPJ, formatCPF, formatPhoneNumber, formatStateRegistration, isValidPhoneNumber, removeNonDigits } from "../../utils";

export type Field = {
    label: string;
    type: string;
    value: string;
    placeholder: string,
    validation?: z.ZodTypeAny; 
    options?: string[]
}

type EntityFormProps = {
    open: boolean;
    type: string;
    data?: string[];
    title: string;
    fields: Field[][]
    formControllers: FormControllers;
    popUpMessage: Dispatch<SetStateAction<string>>;
    popUpController: Dispatch<SetStateAction<boolean>>;
}

export function EntityForm({open, type, title, fields, formControllers, data, popUpMessage, popUpController}: EntityFormProps) {

    const createValidationSchema = (fields: Field[][]) => {
        const shape: Record<string, z.ZodTypeAny> = {};
        
        fields.flat().forEach(field => {
            if (field.validation) {
                shape[field.value] = field.validation; 
            }
        });
        
        return z.object(shape);
    }; 

    const [showErrorPopUp, setShowErrorPopUp] = useState(true);

    const validationSchema = createValidationSchema(fields);
    const { register, watch, control, handleSubmit, formState: { errors }, reset } = useForm({
        resolver: zodResolver(validationSchema),
    });
    const { fields: phoneFields, append, remove } = useFieldArray({
        control,
        name: "phone",
    });

    const onSubmit: SubmitHandler<any> = (formData) => {

        const cleanedData = {
            ...formData,
            phone: formData.phone?.map(removeNonDigits),
            cpf: formData.cpf ? removeNonDigits(formData.cpf) : undefined,
            cnpj: formData.cnpj ? removeNonDigits(formData.cnpj) : undefined,
            stateRegistration: formData.stateRegistration ? removeNonDigits(formData.stateRegistration) : undefined,
        };

        const filteredData = Object.fromEntries(
            Object.entries(cleanedData).filter((entry) => {
                const value = entry[1];
                return (
                    value !== undefined &&
                    value !== null &&
                    (Array.isArray(value) ? value.length > 0 : value !== "")
                );
            })
        );

        console.log(filteredData);

        reset();
        popUpMessage(type === "Adicionar" ? "Adicionado com sucesso" : "Editado com sucesso");
        popUpController(true);
        formControllers.add(false);
        formControllers.edit(false);
    };

    const watchedValues = watch();

    const requiredFields = fields.flat().filter(field => field.validation); 

    const isFormFilled = requiredFields.every(field => {
        const value = watchedValues[field.value];
        return value !== undefined && value !== "" && value.length >= 1;
    });

    const isSubmitDisabled = !isFormFilled;

    const firstKey = Object.keys(errors)[0];
    const firstError = firstKey ? errors[firstKey] : undefined;
    const errorMessage = Array.isArray(firstError) ? "Telefone(s) inválido(s)" : firstError?.message;    

    const handleClose = () => {
        reset();
        formControllers.add(false);
        formControllers.edit(false);
        phoneFields.forEach((_, idx) => remove(idx));
    };

    useEffect(() => {

        const hasPhoneField = fields.flat().some(field => field.value === "phone");

        if (hasPhoneField) {
            append("");
        }

        if (open && type === "Editar" && data) {
            const defaultValues: Record<string, string> = {};

            phoneFields.forEach((_, idx) => remove(idx));
            
            let phoneIndex = 0;
            let fieldIndex = 0; 

            fields.flat().forEach((field) => {
                if (field.value === "phone") {

                    while (isValidPhoneNumber(data[fieldIndex])) {
                        append(data[fieldIndex]);
                        defaultValues[`phone.${phoneIndex++}`] = formatPhoneNumber(data[fieldIndex]);
                        fieldIndex++; 
                    }
                } else {
                    switch (field.value) {
                    case "cpf":
                        defaultValues[field.value] = formatCPF(data[fieldIndex]); 
                        break;
                
                    case "cep":
                        defaultValues[field.value] = formatCEP(data[fieldIndex]);
                        break;

                    case "cnpj":
                        defaultValues[field.value] = formatCNPJ(data[fieldIndex]); 
                        break;
                    
                    case "stateRegistration":
                        defaultValues[field.value] = formatStateRegistration(data[fieldIndex]); 
                        break;

                    default:
                        defaultValues[field.value] = data[fieldIndex];
                        break;
                    }
                    fieldIndex++; 
                }
            });

            reset(defaultValues);

        }
    }, [open, type, data, fields, reset]);

    return (
        <EntityFormWrapper className={open ? "active" : ""} onClick={() => formControllers.add(false)}>
            {errorMessage && showErrorPopUp &&
                <PopUp onClick={(e) => e.stopPropagation()} type="error" message={String(errorMessage)} show={showErrorPopUp} onClose={() => setShowErrorPopUp(false)} />
            }
            <FormWrapper onClick={(e) => e.stopPropagation()} >
                <X size={25} id="close" onClick={handleClose} />
                <h2>{`${type} ${title}`}</h2>
                <form onSubmit={handleSubmit(onSubmit)} noValidate>
                    <div className="inputs-wrapper">
                        {fields.map((fieldArray, indexA) => {
                            return (
                                <div className="fields-wrapper" key={indexA}>
                                    {
                                        fieldArray.map((field, index) => {
                                            if (field.value === "phone") {
                                                return (
                                                    <div className="input-wrapper" key={index}>
                                                        <label htmlFor="phone">{field.label}<Plus size={22} className="phone-controller" onClick={() => append("")} /></label>
                                                        {phoneFields.map((item, idx) => (
                                                            <div key={item.id} className="phone-input-group">
                                                                <input
                                                                    type="text"
                                                                    id={`phone.${idx}`}
                                                                    placeholder={`${field.placeholder} #${idx + 1}`}
                                                                    {...register(`phone.${idx}`, {
                                                                        onChange: (e) => {
                                                                            e.target.value = formatPhoneNumber(e.target.value);
                                                                        }
                                                                    })}
                                                                />
                                                                { phoneFields.length > 1 && idx >= 1 &&
                                                                <Trash size={22} className="phone-controller" onClick={() => remove(idx)} />
                                                                }
                                                            </div>
                                                        ))}
                                                    </div>
                                                );
                                            }
                                            return (
                                                <div className="input-wrapper" key={index}>
                                                    <label htmlFor={field.value}>{field.label}</label>
                                                    {field.type === "select" ? (
                                                        <select
                                                            id={field.value}
                                                            {...register(field.value)}
                                                            defaultValue={""}
                                                        >
                                                            <option value="" disabled>
                                                                {field.placeholder}
                                                            </option>
                                                            {field.options?.map((option: string, idx: number) => (
                                                                <option key={idx} value={option}>
                                                                    {option}
                                                                </option>
                                                            ))}
                                                        </select>
                                                    ) : field.label === "CPF" ? (
                                                        <input
                                                            type={field.type}
                                                            id={field.value}
                                                            placeholder={field.placeholder}
                                                            {...register("cpf", {
                                                                onChange: (e) => {
                                                                    e.target.value = formatCPF(e.target.value);
                                                                }
                                                            })}
                                                        />
                                                    ) : field.label === "CEP" ? (
                                                        <input
                                                            type={field.type}
                                                            id={field.value}
                                                            placeholder={field.placeholder}
                                                            {...register("cep", {
                                                                onChange: (e) => {
                                                                    e.target.value = formatCEP(e.target.value);
                                                                }
                                                            })}
                                                        />
                                                    ): field.label === "CNPJ" ? (
                                                        <input
                                                            type={field.type}
                                                            id={field.value}
                                                            placeholder={field.placeholder}
                                                            {...register("cnpj", {
                                                                onChange: (e) => {
                                                                    e.target.value = formatCNPJ(e.target.value);
                                                                }
                                                            })}
                                                        />
                                                    ) : field.label === "Inscrição Estadual" ? (
                                                        <input
                                                            type={field.type}
                                                            id={field.value}
                                                            placeholder={field.placeholder}
                                                            {...register("stateRegistration", {
                                                                onChange: (e) => {
                                                                    e.target.value = formatStateRegistration(e.target.value);
                                                                }
                                                            })}
                                                        />
                                                    ) : (
                                                        <input
                                                            type={field.type}
                                                            id={field.value}
                                                            placeholder={field.placeholder}
                                                            {...register(field.value)}
                                                        />
                                                    )}
                                                </div>
                                            );
                                        })
                                    }
                                </div>
                            );
                        })}
                    </div>
                    <button type="submit" disabled={isSubmitDisabled}>{type}</button>
                </form>


            </FormWrapper>

        </EntityFormWrapper>
    );

}
