import { ReactNode, useRef, useEffect } from "react";
import { motion, useInView, useAnimation } from "framer-motion";

interface RevealProps {
    children?: ReactNode;
    className?: string;
    type: "div" | "img";
    src?: string,
    alt?: string,
    delay?: number,
    title?: string,
}

export const Reveal = ({ children, className, type, src, alt, delay, title }: RevealProps) => {

    const ref = useRef(null);
    const isInView = useInView(ref, {once: false});

    const mainControls = useAnimation();

    useEffect(() => {
        
        if (isInView) {
            mainControls.start(variants.visible);
        }

    }, [isInView]);

    const variants = {
        hidden: { 
            opacity: 0, y: 100 
        },
        visible: { 
            opacity: 1, y: 0 
        },
    };

    const transition = { 
        duration: 0.5, 
        delay: delay,
    };

    if (type === "div") {
        return (
            <motion.div ref={ref} title={title} className={className} variants={variants} initial="hidden" animate={mainControls} 
                transition={transition}>
                {children}
            </motion.div>
        );
    } else if (type === "img") {
        return (
            <motion.img ref={ref} title={title} className={className} variants={variants} initial="hidden" animate={mainControls} 
                transition={transition}
                src={src}
                alt={alt}
            />
        );
    }

    return null; 
};
