import { AnimatedNumber } from "../../AnimatedNumber";
import { CardWrapper } from "./styles";

type CardProps = {
    icon:  React.ElementType;
    data: number | null;
    text?: string;
    color?: "orange" | "red" | "violet" | "green" | "black";
    type?: string;
    delay?: number;
}

export const Card = ({icon: Icon, data, text, color="black", type, delay = 0}: CardProps) => {

    const isPrice = type === "price";
    const displayValue = isPrice ? <AnimatedNumber value={data ?? 0} isMoney={true} /> : <AnimatedNumber value={data ?? 0} />;

    return (
        <CardWrapper delay={delay}>
            <Icon id="icon" className={color} size={28} />
            <span id="data" className={color}>
                {displayValue}
            </span>
            <p id="text">{text}</p>
        </CardWrapper>
    );

};