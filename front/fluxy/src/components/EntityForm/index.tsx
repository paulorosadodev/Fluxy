import { useForm, SubmitHandler } from "react-hook-form";
import { z } from "zod";
import { zodResolver } from "@hookform/resolvers/zod";
import { FormControllers } from "../DataTable";
import { EntityFormWrapper, FormWrapper } from "./styles";
import { X } from "phosphor-react";
import { Dispatch, SetStateAction, useEffect, useState } from "react";
import { PopUp } from "../PopUp";

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
                shape[field.label] = field.validation; 
            }
        });
        
        return z.object(shape);
    };

    const validationSchema = createValidationSchema(fields);
    const { register, watch, handleSubmit, formState: { errors }, reset } = useForm({
        resolver: zodResolver(validationSchema),
    });

    const onSubmit: SubmitHandler<any> = () => {
        reset();
        popUpMessage(type === "Adicionar" ? "Adicionado com sucesso" : "Editado com sucesso");
        popUpController(true);
        formControllers.add(false);
        formControllers.edit(false);
    };

    const watchedValues = watch();

    const requiredFields = fields.flat().filter(field => field.validation); 

    const isFormFilled = requiredFields.every(field => {
        const value = watchedValues[field.label];
        return value !== undefined && value !== "";
    });

    const isSubmitDisabled = !isFormFilled;

    const [showPopUp, setShowPopUp] = useState(true);

    const firstKey = Object.keys(errors)[0];       
    const firstError = errors[firstKey];   

    const handleClose = () => {
        reset();
        formControllers.add(false);
        formControllers.edit(false);
    };

    let valueIndex = -1;

    useEffect(() => {
        if (open && type === "Editar" && data) {
            const defaultValues: Record<string, string> = {};
    
            fields.flat().forEach((field, index) => {
                defaultValues[field.label] = data[index]; 
            });
    
            reset(defaultValues);
        }
    }, [open, type, data, fields, reset]);

    console.log(data);

    return (
        <EntityFormWrapper className={open ? "active" : ""} onClick={() => formControllers.add(false)}>
            {firstError?.message &&
                <PopUp type="error" message={String(firstError?.message)} show={showPopUp} onClose={() => setShowPopUp(false)} />
            }
            <FormWrapper onClick={(e) => e.stopPropagation()} >
                <X size={25} onClick={handleClose} />
                <h2>{`${type} ${title}`}</h2>

                <form onSubmit={handleSubmit(onSubmit)} noValidate>
                    {fields.map((fieldArray, indexA) => {
                        return (
                            <div className="fields-wrapper" key={indexA}>
                                {
                                    fieldArray.map((field, index) => {
                                        return (
                                            <div className="input-wrapper" key={index}>
                                                <label>{field.label}</label>
                                                {field.type === "select" ? (
                                                    <select {...register(field.label)} defaultValue={data ? data[++valueIndex] : ""}>
                                                        <option value="" disabled>{field.placeholder}</option>
                                                        {field.options?.map((option: string, idx: number) => {
                                                            return (
                                                                <option key={idx}>
                                                                    {option}
                                                                </option>
                                                            );
                                                        })}
                                                    </select>
                                                ) : (
                                                    <input
                                                        type={field.type}
                                                        placeholder={field.placeholder}
                                                        {...register(field.label)} 
                                                    />
                                                )}
                                            </div>
                                        );
                                    })
                                }
                            </div>
                        );
                    })}
                    <button type="submit" disabled={isSubmitDisabled}>{type}</button>
                </form>


            </FormWrapper>

        </EntityFormWrapper>
    );

}
