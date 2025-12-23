/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./src/**/*.{html,ts}",
  ],
  theme: {
    extend: {
      colors: {
        royal: {
          DEFAULT: '#4B2C5E', // Deep Royal Purple
        },
        amethyst: {
          DEFAULT: '#7D5295', // Amethyst Purple
        },
        lavender: {
          DEFAULT: '#AC88BE', // Soft Lavender
        },
        plum: {
          DEFAULT: '#321B3E', // Dark Plum
        },
      },
    },
  },
  plugins: [],
}
