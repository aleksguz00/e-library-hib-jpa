
if [ ! -f "src/main/resources/hibernate.properties.origin" ]; then
    echo "Файл hibernate.properties.origin не найден!"
    exit 1
fi

cp src/main/resources/hibernate.properties.origin src/main/resources/hibernate.properties
