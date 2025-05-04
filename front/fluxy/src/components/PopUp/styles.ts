import styled from "styled-components";

export const PopUpWrapper = styled.div`

    position: absolute;
    z-index: 9999;

    .popUp {
            background-color: white;
            position: fixed;
            bottom: 1.25rem;
            right: 1.25rem;
            padding: 3rem 2rem;

            display: flex;
            align-items: center;
            gap: 0.5rem;
            border-radius: 8px;

            font-weight: bold;

            box-shadow: 3px 3px 10px rgba(0, 0, 0, 0.2);
        }

        #closePopUp {
            position: absolute;
            right: 15px;
            top: 15px;
            z-index: 1;
            transform: scale(1);

            &:hover {
                cursor: pointer;
            }
        }
`;
