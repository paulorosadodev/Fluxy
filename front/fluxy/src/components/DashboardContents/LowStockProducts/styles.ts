import styled from "styled-components";
import { motion } from "framer-motion";

export const LowStockWrapper = styled.div`
    background: white;
    padding: 1.5rem;
    width: 100%;
    flex: 1;
    height: 20rem;
    background-color: ${(props) => props.theme["white-dark"]};
`;

export const Title = styled.h2`
    font-size: 1.2rem;
    margin-bottom: 1rem;
    color: #333;
`;

export const MotionList = styled(motion.ul)`
    list-style: none;
    padding: 0rem 1rem;
    margin: 0;
    overflow-y: auto;
    height: 90%;
`;

export const MotionListItem = styled(motion.li)`
    display: flex;
    align-items: center;
    margin-bottom: 0.75rem;
    background: #fff5f3;
    padding: 0.75rem 1rem;
    border-left: 4px solid ${(props) => props.theme["orange-500"]};
    border-radius: 8px;

    &.stock-zero {
        background: #ffe6e6;
        border-left-color: #d90429; 
    }

    &.stock-low {
        background:rgb(255, 242, 230);
        border-left-color: ${(props) => props.theme["orange-500"]};
    }
    
    &.stock-medium {
        background: #fff7e6;
        border-left-color: ${(props) => props.theme["orange-300"]};
    }
`;

export const AlertIcon = styled.div`
    margin-right: 1rem;

    #warning-circle.stock-zero {
        color: #d90429; 
    }

    #warning-circle.stock-low {
        color: ${(props) => props.theme["orange-500"]};
    }
    
    #warning-circle.stock-medium {
        color: ${(props) => props.theme["orange-300"]};
    }
`;

export const ProductInfo = styled.div`
    display: flex;
    flex-direction: column;
`;

export const ProductName = styled.span`
    font-weight: 600;
    color: #222;
    margin-bottom: 0.25rem;
`;

export const MessageWrapper = styled.div`
    display: flex;
    flex-direction: column;
    align-items: center;
    height: 100%;
    gap: 1rem;

    #svg {
        margin-top: 4rem;
        width: 4rem;
        height: 4rem;
        color: ${(props) => (props.theme["teal-500"])};
    }
`;
