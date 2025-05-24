import { motion } from "framer-motion";
import styled from "styled-components";

export const PieChartWrapper = styled.div`
    background-color: ${(props) => props.theme["white-dark"]};
    height: 20rem;
    width: 100%;
    padding-left: 2rem;
    min-height: 300px;
    display: flex;
    justify-content: center;
    align-items: center;
`;

export const InfoWrapper = styled.div`
    flex: 1;
    height: 100%;
    margin-top: 3rem;
`;

export const Title = styled.h3`
    margin-bottom: 0.5rem;
    font-size: 1.2rem;
    white-space: nowrap;
`;

export const MotionCategoryList = styled(motion.ul)`
    list-style: none;
    padding: 0;
    margin: 0;
`;

export const MotionCategoryItem = styled(motion.li)`
    display: flex;
    align-items: center;
    margin-bottom: 0.1;
`;

export const ColorBox = styled.span<{ color: string }>`
    width: 18px;
    height: 18px;
    background-color: ${({ color }) => color};
    border-radius: 4px;
    margin-right: 0.75rem;
    border: 1px solid #ddd;
`;

export const ChartWrapper = styled.div`
    width: 250px;  
    height: 250px;
`;
