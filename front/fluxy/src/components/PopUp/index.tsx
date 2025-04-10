// SuccessPopUp.tsx

import { Reveal } from "../Reveal";
import { Check, X } from "phosphor-react";
import { PopUpWrapper } from "./styles";

interface PopUpProps {
    message: string,
    show: boolean,
    type: "error" | "success",
    onClose: () => void;
}

export function PopUp({ message, show, type, onClose }: PopUpProps) {

    if (!show) return null;

    return (
        <PopUpWrapper>
            <Reveal type="div" className={"popUp " + type}>
                {type === "success" ? 
                    <Check size={30} color="green" weight="bold" />
                    :
                    <X size={30} color="red" weight="bold" />
                }
                <span>{message}</span>
                <X onClick={onClose} size={20} color="gray" weight="bold" id="closePopUp"
                />
            </Reveal>
        </PopUpWrapper>
    );
}
