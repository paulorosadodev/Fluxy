import { AnimatedNumber } from "../../AnimatedNumber";
import { CardWrapper } from "./styles";

type CardProps = {
    icon:  React.ElementType;
    data: number | null;
    text?: string;
    color?: "orange" | "red" | "violet" | "green" | "black";
    type?: string;
}

export const Card = ({icon: Icon, data, text, color="black", type}: CardProps) => {

    const isPrice = type === "price";
    const displayValue = isPrice ? < AnimatedNumber value={data ?? 0} isMoney={true} /> : <AnimatedNumber value={data ?? 0} />;

    return (
        <CardWrapper>
            <Icon id="icon" className={color} size={28} />
            <span id="data" className={color}>
                {displayValue}
            </span>
            <p id="text">{text}</p>
        </CardWrapper>
    );

};