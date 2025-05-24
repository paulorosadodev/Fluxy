import { motion } from "framer-motion";
import styled from "styled-components";

export const TopTierWrapper = styled.div`
    background-color: ${(props) => props.theme["white-dark"]};
    display: flex;
    flex-direction: column;
    padding: 1.5rem 2rem;

    color: ${(props) => props.theme["brown-800"]};
    height: 20rem;

    h2 {
        font-size: 1.2rem;
        margin-bottom: 1rem;
    }

`;

export const SortInput = styled.div`
    display: flex;
    gap: 1rem;
`;

export const SortSelect = styled.select`
    background-color: ${props => props.theme["gray-200"]};
    color: ${props => props.theme["brown-800"]};
    padding-left: 1rem;
    border-radius: 8px;
    font-size: 0.8rem;
    border: none;
    outline: none;
    cursor: pointer;
    width: 100%;
    height: 2rem;

    option {
        background-color: ${props => props.theme["gray-200"]};
        color: ${props => props.theme["brown-800"]};
    }

    &:hover {
        filter: brightness(1.06);
    }
`;

export const ListWrapper = styled.ol`
    display: flex;
    flex-direction: column;
    gap: 0.75rem;
    margin-top: 1rem;
    padding-right: 0.5rem;
    overflow-y: auto;

    li {
        width: 100%;
        list-style-type: none;
        background-color: ${(props) => props.theme["white"]};
        border-radius: 10px;
        padding: 0.75rem 1rem;
        box-shadow: 0 2px 6px rgba(0,0,0,0.06);
        display: flex;
        justify-content: space-between;
        align-items: center;
        transition: transform 0.2s ease;


        span {
            display: flex;
            align-items: center;
            gap: 0.5rem;

            strong {
                font-size: 1rem;
                color: ${(props) => props.theme["brown-800"]};
            }
        }

        .arrow.up {
            color: ${(props) => props.theme["violet-700"]};
        }

        .arrow.down {
            color: ${(props) => props.theme["orange-500"]};
        }
    }
`;

export const MotionListWrapper = motion(ListWrapper);

export const MotionListItem = styled(motion.li)`
    display: flex;
    align-items: center;
    margin-bottom: 0.1;
`;