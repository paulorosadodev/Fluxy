import js from '@eslint/js'
import globals from 'globals'
import tseslint from 'typescript-eslint'

export default tseslint.config(
    { 
        ignores: ['dist'] 
    },
    {
        extends: [js.configs.recommended, ...tseslint.configs.recommended],
        files: ['**/*.{ts,tsx}'],
        languageOptions: {
            ecmaVersion: 2020,
            globals: globals.browser,
        },
        rules: {
            "semi": ["error", "always"],
            "quotes": ["error", "double"],
            "indent": ["error", 4],
            '@typescript-eslint/no-explicit-any': "off",
            "@typescript-eslint/no-empty-object-type": "off",
        },
    },
)