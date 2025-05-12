import { TouchableOpacity, Text } from 'react-native';
import { styles } from './styles';

interface GenericButtonProps {
    title: string;
    onPress: () => void;
    backgroundColor: 'blue' | 'red' | 'green';
}

export default function GenericButton({ title, onPress, backgroundColor }: GenericButtonProps) {
    return (
        <TouchableOpacity
            onPress={onPress}
            style={[
                styles.button,
                backgroundColor === 'blue' && styles.blue,
                backgroundColor === 'red' && styles.red,
                backgroundColor === 'green' && styles.green,
            ]}
        >
            <Text style={styles.title}>{title}</Text>
        </TouchableOpacity>
    );
}