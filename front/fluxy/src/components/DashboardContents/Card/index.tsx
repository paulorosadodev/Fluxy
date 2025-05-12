import { AnimatedNumber } from "../../AnimatedNumber";
import { CardWrapper } from "./styles";

type CardProps = {
    icon:  React.ElementType;
    data: number | null;
    text?: string;
    color?: "orange" | "red" | "violet" | "green" | "black"
}

export const Card = ({icon: Icon, data, text, color="black"}: CardProps) => {

    return(
        <CardWrapper>
            <Icon id="icon" className={color} size={28} />
            <span id="data" className={color}>
                <AnimatedNumber value={data ?? 0} />
            </span>
            <p id="text">{text}</p>
        </CardWrapper>
    );

};